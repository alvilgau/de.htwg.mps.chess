package core.scala.de.htwg.mps.chess.model

import core.scala.de.htwg.mps.chess.model.Team.Team
import core.scala.de.htwg.mps.chess.model.movement.MoveComplex

case class Knight(var posX: Int, var posY: Int, var team: Team) extends Figure {

  override def getPossibleMoves(board: Board): List[Field] = MoveComplex(2, 1).perform(this, board.fields)
}
