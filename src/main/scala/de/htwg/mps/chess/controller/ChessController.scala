package de.htwg.mps.chess.controller

import de.htwg.mps.chess.model.Board

class ChessController {

  val board = Board(8)

  def boardToString = board.toString
}
