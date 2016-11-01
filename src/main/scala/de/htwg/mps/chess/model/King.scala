package de.htwg.mps.chess.model

import de.htwg.mps.chess.model.Team.Team

case class King(var posX: Int, var posY: Int, var team: Team) extends Figure {

  val MOVES = Array(Array(-1, 0), Array(-1, 1), Array(-1, -1), Array(0, 1),
    Array(0, -1), Array(1, 0), Array(1, 1), Array(-1, -1))

  override def getPossibleMoves(fields: Array[Array[Field]]) = simpleMoveValidation(this, fields, MOVES)
}
