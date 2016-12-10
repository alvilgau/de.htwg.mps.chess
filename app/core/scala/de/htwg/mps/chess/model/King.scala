package core.scala.de.htwg.mps.chess.model

import core.scala.de.htwg.mps.chess.model.Team.Team

case class King(var posX: Int, var posY: Int, var team: Team) extends Figure {

  private val MOVES = List(Array(-1, 0), Array(-1, 1), Array(-1, -1), Array(0, 1),
    Array(0, -1), Array(1, 0), Array(1, 1), Array(1, -1))

  override def getPossibleMoves(board: Board): List[Field] = simpleMoveValidation(this, board, MOVES)

  override def toString = "Ö"
}
