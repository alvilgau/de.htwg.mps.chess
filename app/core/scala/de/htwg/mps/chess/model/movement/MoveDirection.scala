package core.scala.de.htwg.mps.chess.model.movement

import core.scala.de.htwg.mps.chess.model._

trait MoveDirection {
  def filter(field: Field)(implicit figure: Figure): Boolean

  def sort(f1: Field, f2: Field): Boolean
}

case object Down extends MoveDirection {
  override def filter(field: Field)(implicit figure: Figure): Boolean = {
    field.posY < figure.posY
  }

  override def sort(f1: Field, f2: Field): Boolean = {
    f1.posY > f2.posY
  }
}

case object Up extends MoveDirection {
  override def filter(field: Field)(implicit figure: Figure): Boolean = {
    field.posY > figure.posY
  }

  override def sort(f1: Field, f2: Field): Boolean = {
    f1.posY < f2.posY
  }
}

case object Left extends MoveDirection {
  override def filter(field: Field)(implicit figure: Figure): Boolean = {
    field.posX < figure.posX
  }

  override def sort(f1: Field, f2: Field): Boolean = {
    f1.posX > f2.posX
  }
}

case object Right extends MoveDirection {
  override def filter(field: Field)(implicit figure: Figure): Boolean = {
    field.posX > figure.posX
  }

  override def sort(f1: Field, f2: Field): Boolean = {
    f1.posX < f2.posX
  }
}

