package core.scala.de.htwg.mps.chess.controller

import core.scala.de.htwg.mps.chess.model.{Board, Figure}

private object CheckMate extends Enumeration {
  type CheckMate = Value
  val save, check, mate = Value
}

class CheckMate {
  private var checkmateWhite: CheckMate.Value = CheckMate.save
  private var checkmateBlack: CheckMate.Value = CheckMate.save

  def isCheckWhite: Boolean = checkmateWhite == CheckMate.check

  def isCheckBlack: Boolean = checkmateBlack == CheckMate.check

  def isMateWhite: Boolean = checkmateWhite == CheckMate.mate

  def isMateBlack: Boolean = checkmateBlack == CheckMate.mate

  def nextStateWhite(): Unit = checkmateWhite = nextState(checkmateWhite)

  def nextStateBlack(): Unit = checkmateBlack = nextState(checkmateBlack)

  def clear(): Unit = {
    checkmateWhite = CheckMate.save
    checkmateBlack = CheckMate.save
  }

  def update(board: Board, teamWhite: List[Figure], teamBlack: List[Figure]): Unit = {
    checkmateBlack = doCheckMate(board, teamBlack.find(_.isKing).get, teamWhite, checkmateBlack)
    checkmateWhite = doCheckMate(board, teamWhite.find(_.isKing).get, teamBlack, checkmateWhite)
  }

  def getStatusMessage: String = {
    var statusWhite, statusBlack = ""

    checkmateWhite match {
      case CheckMate.save => statusWhite = ""
      case CheckMate.check => statusWhite = "White King is in a check!"
      case _ => statusWhite = "White King is mate. Team Black has won. - Game Over!"
    }

    checkmateBlack match {
      case CheckMate.save => statusBlack = ""
      case CheckMate.check => statusBlack = "Black King is in a check!"
      case _ => statusBlack = "Black King is mate. Team White has won. - Game Over!"
    }

    statusWhite + " " + statusBlack
  }

  private def doCheckMate(board: Board, king: Figure, team: List[Figure], checkMate: CheckMate.Value): CheckMate.Value = {
    val kingField = board.getField(king.posX, king.posY)
    val collision = team.exists(_.getPossibleMoves(board).contains(kingField))
    if (collision) {
      return nextState(checkMate)
    }
    CheckMate.save
  }

  private def nextState(checkMate: CheckMate.Value) = CheckMate(checkMate.id + 1)

}
