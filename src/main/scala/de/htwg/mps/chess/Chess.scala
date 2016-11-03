package de.htwg.mps.chess

import de.htwg.mps.chess.controller.ChessController

object Chess {

  def main(args: Array[String]) {
    val controller = new ChessController()
    println("Welcome to Chess")
    println(controller.boardToString)
  }
}
