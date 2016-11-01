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

  def simpleMoveValidation(figure: Figure, board: Board, moves: Array[Array[Int]]): List[Field] = {
    val possibleMoves = new ListBuffer[Field]

    for (move <- moves) {
      breakable {
        val fieldOption = board.getFieldOption(figure.posX + move(0), figure.posY + move(1))
        if (fieldOption.isEmpty) {
          break()
        }
        checkCollision(figure, fieldOption.get, possibleMoves)
      }
    }

    possibleMoves.toList
  }

  def verticalMoveValidation(figure: Figure, board: Board): List[Field] = {
    val possibleMoves = new ListBuffer[Field]

    // moving right
    breakable {
      for (i <- figure.posX + 1 to board.sizeX) {
        if (checkCollision(figure, board.getField(i, figure.posY), possibleMoves)) {
          break()
        }
      }
    }

    // moving left
    breakable {
      for (i <- figure.posX - 1 to board.MIN_POS by -1) {
        if (checkCollision(figure, board.getField(i, figure.posY), possibleMoves)) {
          break()
        }
      }
    }

    possibleMoves.toList
  }
}
