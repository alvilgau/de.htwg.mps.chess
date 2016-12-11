package models

import java.util.UUID

import akka.actor.{Actor, Props}
import core.scala.de.htwg.mps.chess.Chess
import play.api.libs.json.Json

class GameInstance(gameName: String, player1: Player) {

  class GameInstanceActor extends Actor {
    override def receive: Receive = {
      case _ => println("GAME INSTANCE ACTOR HERE.....")
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
    val json = Json.obj("type" -> data).toString()
    if (run) {
      player.client ! json
    }
  }
}
