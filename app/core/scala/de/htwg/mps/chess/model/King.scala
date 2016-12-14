package core.scala.de.htwg.mps.chess.model

import core.scala.de.htwg.mps.chess.model.Team.Team

case class King(var posX: Int, var posY: Int, var team: Team) extends Figure {

  override def getPossibleMoves(board: Board): List[Field] = {
    verticalMove(this, board, 1) ++ horizontalMove(this, board, 1) ++ diagonalMove(this, board, 1)
  }

  override def toString = "Ö"
}
