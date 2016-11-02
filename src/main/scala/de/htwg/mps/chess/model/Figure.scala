package de.htwg.mps.chess.model

import de.htwg.mps.chess.model.Team.Team

abstract class Figure extends MoveValidator {

  var posX: Int

  var posY: Int

  var team: Team

  def getPossibleMoves(board: Board): List[Field]

  def move(x: Int, y: Int): Boolean = {
    posX = x
    posY = y
    false
  }

  override def toString: String = {
    getClass.getSimpleName.charAt(0).toString
  }

}
