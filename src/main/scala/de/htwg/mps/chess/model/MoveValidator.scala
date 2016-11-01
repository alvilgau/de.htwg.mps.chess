package de.htwg.mps.chess.model

import scala.collection.mutable.ListBuffer
import util.control.Breaks._

trait MoveValidator {

  private def checkCollision(figure: Figure, field: Field, possibleMoves: ListBuffer[Field]): Boolean = {
    if (field.isSet()) {
      if (figure.team != field.figure.get.team) {
        possibleMoves += field
      }
      return true
    }

    possibleMoves += field
    false
  }

  def simpleMoveValidation(figure: Figure, fields: Array[Array[Field]], moves: Array[Array[Int]]): List[Field] = {
    val possibleMoves = new ListBuffer[Field]

    for (move <- moves) {
      breakable {
        val fieldOption = figure.getNeighbourField(move(0), move(1), fields)
        if (fieldOption.isEmpty) {
          break()
        }
        checkCollision(figure, fieldOption.get, possibleMoves)
      }
    }

    possibleMoves.toList
  }

  def verticalMoveValidation(figure: Figure, fields: Array[Array[Field]]): List[Field] = {
    val possibleMoves = new ListBuffer[Field]

    // moving right
    breakable {
      for (i <- figure.posX + 1 to figure.POS_MAX) {
        if (checkCollision(figure, fields(i)(figure.posY), possibleMoves)) {
          break()
        }
      }
    }

    // moving left
    breakable {
      for (i <- figure.posX - 1 to figure.POS_MIN by -1) {
        if (checkCollision(figure, fields(i)(figure.posY), possibleMoves)) {
          break()
        }
      }
    }

    possibleMoves.toList
  }
}
