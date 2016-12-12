package core.scala.de.htwg.mps.chess.controller

import core.scala.de.htwg.mps.chess.model.{Board, Field}

trait Info {
  val board: Board
}

case class InvalidInfo(board: Board, message: String) extends Info

case class ExchangeInfo(board: Board, turnPlayer1: Boolean) extends Info

case class GameoverInfo(board: Board, status: String, checkMate: CheckMate) extends Info

case class UpdateInfo(board: Board, possibleMoves: List[Field], selected: (Int, Int), status: String,
                      turnMessage: String, checkMate: CheckMate, turnPlayer1: Boolean) extends Info
