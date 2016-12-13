package core.scala.de.htwg.mps.chess.model

import core.scala.de.htwg.mps.chess.model.Team.Team
import core.scala.de.htwg.mps.chess.model.movement.Movement

abstract class Figure extends Movement {

  var posX: Int

  var posY: Int

  var team: Team

  def getPossibleMoves(board: Board): List[Field]

  def isKing: Boolean = isInstanceOf[King]

  def move(x: Int, y: Int): Boolean = {
    posX = x
    posY = y
    false
  }

  override def toString: String = getClass.getSimpleName.charAt(0).toString
}
