package models

import java.util.UUID

import akka.actor.{Actor, Props}
import core.scala.de.htwg.mps.chess.Chess

class GameInstance(gameName: String, player1: Player) {

  class GameInstanceActor extends Actor {
    override def receive: Receive = {
      case _ => println("GAME INSTANCE ACTOR HERE.....")
    }
  }

  val gameId: String = UUID.randomUUID().toString

  val player2: Player = null

  var run = false

  val chess = new Chess(gameId)
  chess.system.actorOf(Props(new GameInstanceActor()), "view$wui")

  def getGameName: String = gameName
}
