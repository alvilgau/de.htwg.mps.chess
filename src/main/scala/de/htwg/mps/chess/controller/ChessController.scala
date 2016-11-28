package de.htwg.mps.chess.controller

import de.htwg.mps.chess.controller.Exchange.ExchangeValue
import de.htwg.mps.chess.model.Team._
import de.htwg.mps.chess.model._
import de.htwg.mps.chess.util.Observable

class ChessController extends Observable {

  val board = Board(8)

  var selected = false

  var exchange = false

  var gameover = false

  var moveFigure: Figure = _

  var turn: Team = Team.white

  val checkMate = new CheckMate()

  var status = "Welcome to Chess"

  initFigures()

  private def initFigures(): Unit = {
    initFigures(0, Team.white)
    initFigures(7, Team.black)
    initPawns(1, Team.white)
    initPawns(6, Team.black)
  }

  private def initFigures(posY: Int, team: Team) = {
    board.getFields(posY).foreach(field =>
      field.posX match {
        case 0 | 7 => field.figure = Some(Rook(field.posX, posY, team))
        case 1 | 6 => field.figure = Some(Knight(field.posX, posY, team))
        case 2 | 5 => field.figure = Some(Bishop(field.posX, posY, team))
        case 3 => field.figure = Some(Queen(field.posX, posY, team))
        case 4 => field.figure = Some(King(field.posX, posY, team))
        case _ => field.figure = Some(Rook(field.posX, posY, team))
      }
    )
  }

  private def initPawns(posY: Int, team: Team) = {
    board.getFields(posY).foreach(field =>
      field.figure = Some(Pawn(field.posX, posY, team))
    )
  }

  def handleMovement(x: Int, y: Int): Unit = {
    if (gameover || exchange) {
      return
    }

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
      exchange = moveFigure.move(x, y)
      board.setFigure(moveFigure)

      // set new team for next turn
      turn = if (turn == Team.white) Team.black else Team.white

      updateCheckmate()

      status = "Figure was moved successfully."
    } else {
      status = "Move failed. No Figure is selected."
    }

    selected = false
    notifyObservers()
  }

  private def updateCheckmate() = {
    val teamWhite = board.getFigures(Team.white)
    val teamBlack = board.getFigures(Team.black)
    checkMate.update(board, teamWhite, teamBlack)

    if (checkMate.isCheckWhite && turn == Team.black) {
      checkMate.nextStateWhite()
    } else if (checkMate.isCheckBlack && turn == Team.white) {
      checkMate.nextStateBlack()
    }

    gameover = checkMate.isMateWhite || checkMate.isMateBlack
  }

  def restart(): Unit = {
    selected = false
    exchange = false
    gameover = false
    moveFigure = null
    turn = Team.white
    checkMate.clear()
    status = "Welcome to Chess"
    board.init()
    initFigures()
    notifyObservers()
  }

  def doExchange(exchangeValue: ExchangeValue): Unit = {
    // create figure by reflection
    val constructor = exchangeValue.clazz.getConstructors.head
    val instance = constructor.newInstance(new Integer(moveFigure.posX), new Integer(moveFigure.posY), moveFigure.team)
    val figure = instance.asInstanceOf[Figure]

    board.setFigure(figure)
    exchange = false
    status += " " + exchangeValue.toString.capitalize + " was chosen."
    updateCheckmate()
    notifyObservers()
  }

  def getTurnMessage: String = "Team " + turn + "'s turn"

}
