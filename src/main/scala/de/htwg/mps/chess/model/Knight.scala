package de.htwg.mps.chess.model

import de.htwg.mps.chess.model.Team.Team

case class Knight(var posX: Int, var posY: Int, var team: Team) extends Figure {

  val MOVES = List(Array(-2, 1), Array(-1, 2), Array(1, 2), Array(2, 1),
    Array(2, -1), Array(1, -2), Array(-1, -2), Array(-2, -1))

  override def getPossibleMoves(board: Board) = simpleMoveValidation(this, board, MOVES)
}
