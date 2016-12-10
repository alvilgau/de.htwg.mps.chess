package core.scala.de.htwg.mps.chess.model

import core.scala.de.htwg.mps.chess.model.Team.Team

case class Rook(var posX: Int, var posY: Int, var team: Team) extends Figure {

  override def getPossibleMoves(board: Board): List[Field] = horizontalMoveValidation(this, board) ++ verticalMoveValidation(this, board)
}
