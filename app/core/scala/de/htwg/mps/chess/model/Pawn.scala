package core.scala.de.htwg.mps.chess.model

import core.scala.de.htwg.mps.chess.model.Team.Team
import core.scala.de.htwg.mps.chess.model.movement._

case class Pawn(var posX: Int, var posY: Int, var team: Team) extends Figure {

  private val startPosY = posY

  override def getPossibleMoves(board: Board): List[Field] = {
    val dY = if (posY == startPosY) 2 else 1
    var direction: MoveDirection = Down
    if (team == Team.white) {
      direction = Up
    }

    val moves = List(
      MoveSimple(direction, ValidationWithoutKill),
      MoveDiagonal(Right, direction, ValidationKill),
      MoveDiagonal(Left, direction, ValidationKill)
    )
    moves.flatMap(_.perform(this, board.fields).take(dY))
  }

  override def move(x: Int, y: Int): Boolean = {
    posX = x
    posY = y

    if (team == Team.white) {
      posY == Board.MAX_POS
    } else {
      posY == Board.MIN_POS
    }
  }
}
