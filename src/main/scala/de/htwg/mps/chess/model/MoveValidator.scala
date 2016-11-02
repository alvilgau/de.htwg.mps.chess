package de.htwg.mps.chess.model

import scala.collection.mutable.ListBuffer
import util.control.Breaks._

trait MoveValidator {

  private def checkCollision(figure: Figure, field: Field, possibleMoves: ListBuffer[Field]): Boolean = {
    if (field.isSet) {
      if (figure.team != field.figure.get.team) {
        possibleMoves += field
      }
      return true
    }

    possibleMoves += field
    false
  }

  def simpleMoveValidation(figure: Figure, board: Board, moves: List[Array[Int]]): List[Field] = {
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

  def horizontalMoveValidation(figure: Figure, board: Board): List[Field] = {
    val possibleMoves = new ListBuffer[Field]

    // moving up
    breakable {
      for (i <- figure.posX + 1 to board.sizeY) {
        if (checkCollision(figure, board.getField(figure.posX, i), possibleMoves)) {
          break()
        }
      }
    }

    // moving down
    breakable {
      for (i <- figure.posX - 1 to board.MIN_POS by -1) {
        if (checkCollision(figure, board.getField(figure.posX, i), possibleMoves)) {
          break()
        }
      }
    }

    possibleMoves.toList
  }

  def diagonalMoveValidation(figure: Figure, board: Board): List[Field] = {
    val possibleMoves = new ListBuffer[Field]

    // moving left-up
    var x = figure.posX - 1
    var y = figure.posY + 1
    breakable {
      while (x >= board.MIN_POS && y <= board.sizeY) {
        if (checkCollision(figure, board.getField(x, y), possibleMoves)) {
          break()
        }
        x -= 1
        y += 1
      }
    }

    // moving left-down
    breakable {
      x = figure.posX - 1
      y = figure.posY - 1
      while (x >= board.MIN_POS && y >= board.MIN_POS) {
        if (checkCollision(figure, board.getField(x, y), possibleMoves)) {
          break()
        }
        x -= 1
        y -= 1
      }
    }

    // moving right-up
    x = figure.posX + 1
    y = figure.posY + 1
    breakable {
      while (x <= board.sizeX && y <= board.sizeY) {
        if (checkCollision(figure, board.getField(x, y), possibleMoves)) {
          break()
        }
        x += 1
        y += 1
      }
    }

    // moving right-down
    x = figure.posX + 1
    y = figure.posY - 1
    breakable {
      while (x <= board.sizeX && y >= board.MIN_POS) {
        if (checkCollision(figure, board.getField(x, y), possibleMoves)) {
          break()
        }
        x += 1
        y -= 1
      }
    }

    possibleMoves.toList
  }
}
