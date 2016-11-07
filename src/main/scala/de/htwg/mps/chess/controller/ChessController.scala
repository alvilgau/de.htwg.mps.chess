package de.htwg.mps.chess.controller

import de.htwg.mps.chess.model.{Board, Figure, Team}
import de.htwg.mps.chess.util.Observable

class ChessController extends Observable {

  val board = Board(8)

  var selected = false

  var moveFigure: Figure = null

  var turn = Team.white

  var status = "Welcome to Chess"

  def handleMovement(x: Int, y: Int) = {
    if (!selected) {
      select(x, y)
    } else {
      move(x, y)
    }
  }

  private def select(x: Int, y: Int) = {
    val field = board.getField(x, y)
    if (field.isSet && turn == field.figure.get.team) {
      moveFigure = field.figure.get
      val possibleMoves = moveFigure.getPossibleMoves(board)
      if (possibleMoves.nonEmpty) {
        selected = true
        status = "One Figure is selected."
        notifyObservers()
      }
    } else {
      status = "No Figure is selected."
      notifyObservers()
    }
  }

  private def move(x: Int, y: Int) = {
    val fieldOld = board.getField(moveFigure.posX, moveFigure.posY)
    val fieldNew = board.getField(x, y)
    if (moveFigure.getPossibleMoves(board).contains(fieldNew)) {
      // clear old field
      fieldOld.clear()

      // move figure
      moveFigure.move(x, y)
      board.setFigure(moveFigure)

      // set new team for next turn
      turn = if (turn == Team.white) Team.black else Team.white

      status = "Figure was moved successfully."
    } else {
      status = "Move failed. No Figure is selected."
    }

    selected = false
    notifyObservers()
  }

  def getTurnMessage = "Team " + turn + "'s turn"

}
