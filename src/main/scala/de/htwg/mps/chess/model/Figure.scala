package de.htwg.mps.chess.model

import de.htwg.mps.chess.model.Team.Team

abstract class Figure extends MoveValidator {

  val POS_MAX = 7
  val POS_MIN = 0

  var posX: Int

  var posY: Int

  var team: Team

  def getPossibleMoves(fields: Array[Array[Field]]): List[Field]

  def move(x: Int, y: Int): Boolean = {
    posX = x
    posY = y
    false
  }

  def getNeighbourField(x: Int, y: Int, fields: Array[Array[Field]]): Option[Field] = {
    val newPosX = posX + x
    val newPosY = posY + y

    if (newPosX > POS_MAX || newPosY > POS_MAX || newPosX < POS_MIN || newPosY < POS_MIN) {
      return None
    }

    Some(fields(newPosX)(newPosY))
  }

}
