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
        var json = Json.obj("fields" -> boardToDTO(info))
        json = json + ("statusMessage" -> JsString(info.status))
        json = json + ("checkmateMessage" -> JsString(info.checkMate.getStatusMessage))
        if (info.checkMate.isMateBlack) {
          notifyGameover(json, player1, player2)
        } else {
          notifyGameover(json, player2, player1)
        }

      // exchange
      case info: ExchangeInfo =>
        var json = Json.obj("fields" -> boardToDTO(info))
        json = json + ("exchange" -> JsBoolean(true))
        notifyPlayer(info.turnPlayer1, json.toString)

      // update
      case info: UpdateInfo =>
        turnPlayer1 = info.turnPlayer1
        var json = boardToJson(info)
        json = json + ("statusMessage" -> JsString(info.status))
        json = json + ("checkmateMessage" -> JsString(info.checkMate.getStatusMessage))
        json = json + ("turnMessage" -> JsString(info.turnMessage))
        if (info.selected == null) {
          player1.client ! json.toString
          player2.client ! json.toString
        } else {
          notifyPlayer(info.turnPlayer1, json.toString)
        }
    }

    private def boardToJson(info: UpdateInfo): JsObject = {
      val fields: List[FieldDTO] = boardToDTO(info)
      if (info.selected != null) {
        // set selected field
        fields.find(f => f.posX == info.selected._1 && f.posY == info.selected._2).get.highlight = "selected "

        // set fields on the can be moved
        fields.filter(f => info.possibleMoves.exists(p => p.posX == f.posX && p.posY == f.posY))
          .foreach(_.highlight += "possible")
      }

      Json.obj("fields" -> fields)
    }

    private def boardToDTO(info: Info): List[FieldDTO] = {
      info.board.fields
        .map(FieldDTO.fromField)
        .sortWith(_.posX < _.posX)
        .sortWith(_.posY > _.posY)
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

  val chess = new Chess(gameId)
  chess.system.actorOf(Props(new GameInstanceActor()), "view$wui")

  var turnPlayer1: Boolean = _

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
    if (player1.equals(player)) {
      player2.client ! Json.obj("type" -> "won").toString
    } else {
      player1.client ! Json.obj("type" -> "won").toString
    }
    finish()
  }

  private def finish(): Unit = {
    player1.clear()
    player1 = null
    player2.clear()
    player2 = null
    run = false
    finished = true
    chess.system.terminate()
  }

}
