package de.htwg.mps.chess

import de.htwg.mps.chess.controller.ChessController
import de.htwg.mps.chess.model.{Pawn, Team}

object Chess {

  def main(args: Array[String]) {
    val controller = new ChessController()
    println("Welcome to Chess")
    println(controller.boardToString)

    val pawn = new Pawn(1, 1, Team.white, 1)
    controller.board.setFigure(pawn)
    println(controller.boardToString)

    controller.handleMovement(1, 1)
    controller.handleMovement(1, 2)
    println(controller.boardToString)
  }
}
