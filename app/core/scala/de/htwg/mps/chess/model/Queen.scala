package core.scala.de.htwg.mps.chess.model

import core.scala.de.htwg.mps.chess.model.Team.Team

case class Queen(var posX: Int, var posY: Int, var team: Team) extends Figure {

  override def getPossibleMoves(board: Board): List[Field] = verticalMoveValidation(this, board) ++ horizontalMoveValidation(this, board) ++ diagonalMoveValidation(this, board)
}
