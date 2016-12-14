package core.scala.de.htwg.mps.chess.model

import core.scala.de.htwg.mps.chess.model.Team.Team

import scala.collection.mutable.ListBuffer

case class Pawn(var posX: Int, var posY: Int, var team: Team) extends Figure {

  private val startPosY = posY

  // TODO: use validation of Movement.class
  override def getPossibleMoves(board: Board): List[Field] = {
    val possibleMoves = new ListBuffer[Field]
    val dY = if (team == Team.white) 1 else -1

    // move 1 field forward
    var fieldOption = board.getFieldOption(posX, posY + dY)
    if (fieldOption.isDefined && !fieldOption.get.isSet) {
      possibleMoves += fieldOption.get

      // move 2 field forward
      if (posY == startPosY) {
        val dY2 = if (team == Team.white) 2 else -2
        fieldOption = board.getFieldOption(posX, posY + dY2)
        if (fieldOption.isDefined && !fieldOption.get.isSet) {
          possibleMoves += fieldOption.get
        }
      }
    }

    // possible kills
    possibleKill(1, dY, board, possibleMoves)
    possibleKill(-1, dY, board, possibleMoves)

    possibleMoves.toList
  }

  private def possibleKill(dX: Int, dY: Int, board: Board, possibleMoves: ListBuffer[Field]) = {
    val fieldOption = board.getFieldOption(posX + dX, posY + dY)
    if (fieldOption.isDefined) {
      val field = fieldOption.get
      if (field.isSet && field.figure.get.team != team) {
        possibleMoves += field
      }
    }
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
