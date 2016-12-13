package core.scala.de.htwg.mps.chess.model.movement

import core.scala.de.htwg.mps.chess.model._

trait MoveValidation {
  def validate(fields: List[Field])(implicit figure: Figure): List[Field] = {
    val index = fields.indexWhere(_.isSet)
    if (index == -1) {
      return fields
    }
    val list = fields.take(index + 1).reverse
    if (list.head.figure.get.team == figure.team) {
      return list.tail
    }
    list
  }
}

case class Move(md: MoveDirection) extends MoveValidation {
  private def sameDirection(field: Field)(implicit figure: Figure): Boolean = {
    md match {
      case Left | Right => field.posY == figure.posY
      case Up | Down => field.posX == figure.posX
    }
  }

  def perform(figure: Figure, fields: List[Field]): List[Field] = {
    implicit val fig: Figure = figure
    val list = fields.filter(md.filter)
      .filter(sameDirection)
      .sortWith(md.sort)
    validate(list)
  }
}

case class MoveDiagonal(md1: MoveDirection, md2: MoveDirection) extends MoveValidation {
  private def distance(v1: Int, v2: Int): Int = {
    Math.max(v1, v2) - Math.min(v1, v2)
  }

  private def sameDistance(field: Field)(implicit figure: Figure): Boolean = {
    distance(field.posX, figure.posX) == distance(field.posY, figure.posY)
  }

  def perform(figure: Figure, fields: List[Field]): List[Field] = {
    implicit val fig: Figure = figure
    val list = fields.filter(md1.filter)
      .filter(md2.filter)
      .filter(sameDistance)
      .sortWith(md1.sort)
    validate(list)
  }
}