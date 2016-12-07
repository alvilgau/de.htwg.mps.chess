package de.htwg.mps.chess

import akka.actor.{ActorSystem, Props}
import de.htwg.mps.chess.aview.tui.TextUI
import de.htwg.mps.chess.controller.ChessController

object Chess {

  def main(args: Array[String]) {
    val system = ActorSystem("ChessSystem")
    val tui = system.actorOf(Props[TextUI], name = "view$tui")
    system.actorOf(Props[ChessController], "controller")
    while (true) {
      val input = scala.io.StdIn.readLine()
      tui ! input
    }
  }
}
