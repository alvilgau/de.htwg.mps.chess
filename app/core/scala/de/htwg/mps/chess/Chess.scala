package core.scala.de.htwg.mps.chess

import akka.actor.{ActorRef, ActorSystem, Props}
import core.scala.de.htwg.mps.chess.aview.tui.TextUI
import core.scala.de.htwg.mps.chess.controller.ChessController

class Chess() {
  val system = ActorSystem("ChessSystem")
  val tui: ActorRef = system.actorOf(Props[TextUI], "view$tui")
  var controller: ActorRef = _

  def start(): Unit = {
    controller = system.actorOf(Props[ChessController], "controller")
  }
}

object Chess {

  def main(args: Array[String]) {
    val chess = new Chess()
    chess.start()

    while (true) {
      val input = scala.io.StdIn.readLine()
      chess.tui ! input
    }
  }
}
