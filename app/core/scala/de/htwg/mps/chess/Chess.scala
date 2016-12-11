package core.scala.de.htwg.mps.chess

import akka.actor.{ActorRef, ActorSystem, Props}
import core.scala.de.htwg.mps.chess.aview.tui.TextUI
import core.scala.de.htwg.mps.chess.controller.ChessController

class Chess(id: String) {
  val system = ActorSystem("ChessSystem" + id)
  val tui: ActorRef = system.actorOf(Props[TextUI], "view$tui")
  val controller: ActorRef = system.actorOf(Props[ChessController], "controller")
}

object Chess {

  def main(args: Array[String]) {
    val chess = new Chess("1")

    while (true) {
      val input = scala.io.StdIn.readLine()
      chess.tui ! input
    }
  }
}
