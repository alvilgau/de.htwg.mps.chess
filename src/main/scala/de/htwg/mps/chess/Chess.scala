package de.htwg.mps.chess

import de.htwg.mps.chess.aview.tui.TextUI
import de.htwg.mps.chess.controller.ChessController

object Chess {

  def main(args: Array[String]) {
    val controller = new ChessController()
    val tui = new TextUI(controller)

    var run = true
    while (run) {
      val input = scala.io.StdIn.readLine()
      run = tui.processInputLine(input)
    }

  }
}
