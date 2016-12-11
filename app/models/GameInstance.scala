package models

import java.util.UUID

import akka.actor.{Actor, Props}
import core.scala.de.htwg.mps.chess.Chess
import core.scala.de.htwg.mps.chess.controller._
import play.api.libs.json._

class GameInstance(gameName: String, player1: Player) {

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
        player1.client ! json.toString
      //        player2.client ! json

      // update
      case info: UpdateInfo =>
        var json = boardToJson(info)
        if (info.selected != null) {
          json = json + ("selectedX" -> JsNumber(info.selected._1))
          json = json + ("selectedY" -> JsNumber(info.selected._2))
        }
        json = json + ("statusMessage" -> JsString(info.status))
        json = json + ("checkmateMessage" -> JsString(info.checkMate.getStatusMessage))
        json = json + ("turnMessage" -> JsString(info.turnMessage))
        player1.client ! json.toString
      //        player2.client ! json
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

    private def notifyGameover(board: JsObject, playerWon: Player, playerLost: Player): Unit = {
      var json = board + ("type" -> JsString("won"))
      player1.client ! json.toString
      json = board + ("type" -> JsString("lost"))
      //      player2.client ! json.toString
    }
  }

  player1.game = this

  val gameId: String = UUID.randomUUID().toString

  var player2: Player = _

  var run = false

  val chess = new Chess(gameId)
  chess.system.actorOf(Props(new GameInstanceActor()), "view$wui")

  def getGameName: String = gameName

  def join(player: Player): Unit = {
    run = true
    player2 = player
    player2.game = this
    player.client ! Json.obj("type" -> "start").toString
  }

}
