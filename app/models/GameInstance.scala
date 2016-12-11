package models

import java.util.UUID

import akka.actor.{Actor, Props}
import core.scala.de.htwg.mps.chess.Chess
import core.scala.de.htwg.mps.chess.controller.Info
import play.api.libs.json.{JsObject, JsString, Json}

class GameInstance(gameName: String, player1: Player) {

  class GameInstanceActor extends Actor {
    override def receive: Receive = {
      case info: Info =>
        var json = infoToJson(info)
        json = json + ("test" -> JsString("awdw"))

        player1.client ! json.toString
    }

    private def infoToJson(info: Info): JsObject = {
      val fields: List[FieldDTO] = info.board.fields
        .map(FieldDTO.fromField)
        .sortWith(_.posX < _.posX)
        .sortWith(_.posY > _.posY)
      Json.obj("fields" -> fields)
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
    notifyPlayer(player1, "start")
  }

  private def notifyPlayer(player: Player, data: String): Unit = {
    val json = Json.obj("type" -> data).toString
    if (run) {
      player.client ! json
    }
  }
}
