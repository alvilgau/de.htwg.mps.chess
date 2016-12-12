package models

import java.util.UUID

import akka.actor.{Actor, Props}
import core.scala.de.htwg.mps.chess.Chess
import core.scala.de.htwg.mps.chess.controller._
import play.api.libs.json._

class GameInstance(gameName: String, var player1: Player) {

  class GameInstanceActor extends Actor {

    override def receive: Receive = {
      // game over
      case info: GameoverInfo =>
        val json: JsObject = Json.toJson(InfoDTO.fromInfo(info)).as[JsObject]
        if (info.checkMate.isMateBlack) {
          notifyGameover(json, player1, player2)
        } else {
          notifyGameover(json, player2, player1)
        }

      // exchange
      case info: ExchangeInfo =>
        var json: JsObject = Json.toJson(InfoDTO.fromInfo(info)).as[JsObject]
        json = json + ("exchange" -> JsBoolean(true))
        notifyPlayer(info.turnPlayer1, json.toString)

      // update
      case info: UpdateInfo =>
        val dto = InfoDTO.fromInfo(info)
        val json: JsObject = Json.toJson(dto).as[JsObject]
        data = Json.toJson(dto.fields)
        turnPlayer1 = info.turnPlayer1
        if (info.selected == null) {
          player1.client ! json.toString
          player2.client ! json.toString
        } else {
          notifyPlayer(info.turnPlayer1, json.toString)
        }
    }

    def notifyPlayer(turnPlayer1: Boolean, data: String): Unit = {
      if (turnPlayer1) {
        player1.client ! data
      } else {
        player2.client ! data
      }
    }

    private def notifyGameover(board: JsObject, playerWon: Player, playerLost: Player): Unit = {
      var json = board + ("type" -> JsString("won"))
      player1.client ! json.toString
      json = board + ("type" -> JsString("lost"))
      player2.client ! json.toString
      finish()
    }
  }

  player1.game = this

  val gameId: String = UUID.randomUUID().toString

  var player2: Player = _

  var run = false

  var finished = false

  val chess = new Chess()
  chess.system.actorOf(Props(new GameInstanceActor()), "view$wui")

  var turnPlayer1: Boolean = _

  var data: JsValue = _

  def getGameName: String = gameName

  def join(player: Player): Unit = {
    run = true
    player2 = player
    player2.game = this
    player1.client ! Json.obj("type" -> "start").toString
    chess.start()
  }

  def isCurrentTurn(player: Player): Boolean = {
    if (turnPlayer1) {
      player1.equals(player)
    } else {
      player2.equals(player)
    }
  }

  def leave(player: Player): Unit = {
    if (player1.equals(player) && player2 != null) {
      player2.client ! Json.obj("type" -> "won").toString
    } else if (player1 != null) {
      player1.client ! Json.obj("type" -> "won").toString
    }
    finish()
  }

  private def finish(): Unit = {
    if (player1 != null) {
      player1.clear()
      player1 = null
    }
    if (player2 != null) {
      player2.clear()
      player2 = null
    }
    run = false
    finished = true
    chess.controller ! QuitCmd
  }

}
