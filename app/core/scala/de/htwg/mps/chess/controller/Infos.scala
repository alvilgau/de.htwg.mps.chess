package core.scala.de.htwg.mps.chess.controller

import core.scala.de.htwg.mps.chess.model.Board

trait Info {
  val board: Board
}

case class InvalidInfo(board: Board, message: String) extends Info

case class ExchangeInfo(board: Board) extends Info

case class GameoverInfo(board: Board, status: String, checkMate: CheckMate) extends Info

case class UpdateInfo(board: Board, status: String, turnMessage: String, checkMate: CheckMate) extends Info