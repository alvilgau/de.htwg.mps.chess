package de.htwg.mps.chess.controller

trait Info {
  val board: String
}

case class InvalidInfo(board: String, message: String) extends Info

case class ExchangeInfo(board: String) extends Info

case class GameoverInfo(board: String, status: String, checkMate: CheckMate) extends Info

case class UpdateInfo(board: String, status: String, turnMessage: String, checkMate: CheckMate) extends Info