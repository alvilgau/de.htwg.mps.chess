package core.scala.de.htwg.mps.chess.model

import core.scala.de.htwg.mps.chess.model.Team.Team

case class Knight(var posX: Int, var posY: Int, var team: Team) extends Figure {

  private val MOVES = List(Array(-2, 1), Array(-1, 2), Array(1, 2), Array(2, 1),
    Array(2, -1), Array(1, -2), Array(-1, -2), Array(-2, -1))

  override def getPossibleMoves(board: Board): List[Field] = simpleMove(this, board, MOVES)
}
