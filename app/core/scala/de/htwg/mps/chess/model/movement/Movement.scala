package core.scala.de.htwg.mps.chess.model.movement

import core.scala.de.htwg.mps.chess.model._

import scala.collection.mutable.ListBuffer

trait Move {
  def perform(figure: Figure, fields: List[Field]): List[Field]
}

case class MoveSimple(md: MoveDirection, mv: MoveValidation = ValidationWithKill) extends Move {
  private def sameDirection(field: Field)(implicit figure: Figure): Boolean = {
    md match {
      case Left | Right => field.posY == figure.posY
      case Up | Down => field.posX == figure.posX
    }
  }

  override def perform(figure: Figure, fields: List[Field]): List[Field] = {
    implicit val fig: Figure = figure
    val list = fields.filter(md.filter)
      .filter(sameDirection)
      .sortWith(md.sort)
    mv.validate(list)
  }
}

case class MoveDiagonal(md1: MoveDirection, md2: MoveDirection, mv: MoveValidation = ValidationWithKill) extends Move {
  private def distance(v1: Int, v2: Int): Int = {
    Math.max(v1, v2) - Math.min(v1, v2)
  }

  private def sameDistance(field: Field)(implicit figure: Figure): Boolean = {
    distance(field.posX, figure.posX) == distance(field.posY, figure.posY)
  }

  override def perform(figure: Figure, fields: List[Field]): List[Field] = {
    implicit val fig: Figure = figure
    val list = fields.filter(md1.filter)
      .filter(md2.filter)
      .filter(sameDistance)
      .sortWith(md1.sort)
    mv.validate(list)
  }
}

case class MoveComplex(dx: Int, dy: Int) extends Move {
  private def getAllPossibilities(figure: Figure, dx: Int, dy: Int): List[MoveComplex] = {
    val list = new ListBuffer[MoveComplex]
    list += MoveComplex(figure.posX + dx, figure.posY + dy)
    list += MoveComplex(figure.posX + dx, figure.posY - dy)
    list += MoveComplex(figure.posX - dx, figure.posY + dy)
    list += MoveComplex(figure.posX - dx, figure.posY - dy)
    list.toList
  }

  override def perform(figure: Figure, fields: List[Field]): List[Field] = {
    val possibilities = getAllPossibilities(figure, dx, dy) ++ getAllPossibilities(figure, dy, dx)
    fields.filter(f => possibilities.exists(p => p.dx == f.posX && p.dy == f.posY))
      .filter(f => !f.isSet || (f.isSet && f.figure.get.team != figure.team))
  }
}

trait Movement {

  def verticalMove(figure: Figure, board: Board): List[Field] = {
    verticalMove(figure, board, board.size)
  }

  def verticalMove(figure: Figure, board: Board, numberOfSteps: Int): List[Field] = {
    MoveSimple(Left).perform(figure, board.fields).take(numberOfSteps) ++
      MoveSimple(Right).perform(figure, board.fields).take(numberOfSteps)
  }

  def horizontalMove(figure: Figure, board: Board): List[Field] = {
    horizontalMove(figure, board, board.size)
  }

  def horizontalMove(figure: Figure, board: Board, numberOfSteps: Int): List[Field] = {
    MoveSimple(Up).perform(figure, board.fields).take(numberOfSteps) ++
      MoveSimple(Down).perform(figure, board.fields).take(numberOfSteps)
  }

  def diagonalMove(figure: Figure, board: Board): List[Field] = {
    diagonalMove(figure, board, board.size)
  }

  def diagonalMove(figure: Figure, board: Board, numberOfSteps: Int): List[Field] = {
    MoveDiagonal(Left, Up).perform(figure, board.fields.take(numberOfSteps)) ++
      MoveDiagonal(Left, Down).perform(figure, board.fields).take(numberOfSteps) ++
      MoveDiagonal(Right, Up).perform(figure, board.fields).take(numberOfSteps) ++
      MoveDiagonal(Right, Down).perform(figure, board.fields).take(numberOfSteps)
  }

}
