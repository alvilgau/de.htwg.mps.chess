package core.scala.de.htwg.mps.chess.model.movement

import core.scala.de.htwg.mps.chess.model._

trait Movement {

  def verticalMove(figure: Figure, board: Board): List[Field] = {
    Move(Left).perform(figure, board.fields) ++
      Move(Right).perform(figure, board.fields)
  }

  def horizontalMove(figure: Figure, board: Board): List[Field] = {
    Move(Up).perform(figure, board.fields) ++
      Move(Down).perform(figure, board.fields)
  }

  def diagonalMove(figure: Figure, board: Board): List[Field] = {
    MoveDiagonal(Left, Up).perform(figure, board.fields) ++
      MoveDiagonal(Left, Down).perform(figure, board.fields) ++
      MoveDiagonal(Right, Up).perform(figure, board.fields) ++
      MoveDiagonal(Right, Down).perform(figure, board.fields)
  }

  def simpleMove(figure: Figure, board: Board, moves: List[Array[Int]]): List[Field] = {
    board.fields.filter { f =>
      moves.exists(m => (m(0) + figure.posX) == f.posX
        && (m(1) + figure.posY) == f.posY)
    }.filter(f => !f.isSet || (f.isSet && f.figure.get.team != figure.team))
  }

}
