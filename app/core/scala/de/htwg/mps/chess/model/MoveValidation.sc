import core.scala.de.htwg.mps.chess.model._

val board = new Board(8)
val fig = Pawn(3, 3, Team.white)

trait MoveDirection {
  def filter(field: Field)(implicit figure: Figure): Boolean

  def sort(f1: Field, f2: Field): Boolean
}

case object MoveDown extends MoveDirection {
  override def filter(field: Field)(implicit figure: Figure): Boolean = {
    field.posY < figure.posY
  }

  override def sort(f1: Field, f2: Field): Boolean = {
    f1.posY > f2.posY
  }
}

case object MoveUp extends MoveDirection {
  override def filter(field: Field)(implicit figure: Figure): Boolean = {
    field.posY > figure.posY
  }

  override def sort(f1: Field, f2: Field): Boolean = {
    f1.posY < f2.posY
  }
}


case object MoveLeft extends MoveDirection {
  override def filter(field: Field)(implicit figure: Figure): Boolean = {
    field.posX < figure.posX
  }

  override def sort(f1: Field, f2: Field): Boolean = {
    f1.posX > f2.posX
  }
}

case object MoveRight extends MoveDirection {
  override def filter(field: Field)(implicit figure: Figure): Boolean = {
    field.posX > figure.posX
  }

  override def sort(f1: Field, f2: Field): Boolean = {
    f1.posX < f2.posX
  }
}

class Diagonal(md1: MoveDirection, md2: MoveDirection) {
  private def distance(v1: Int, v2: Int): Int = {
    Math.max(v1, v2) - Math.min(v1, v2)
  }

  private def sameDistance(field: Field)(implicit figure: Figure): Boolean = {
    distance(field.posX, figure.posX) == distance(field.posY, figure.posY)
  }

  def perform(fields: List[Field], figure: Figure): List[Field] = {
    implicit val f: Figure = figure
    fields.filter(md1.filter)
      .filter(md2.filter)
      .filter(sameDistance)
      .sortWith(md1.sort)
  }
}

case object MoveLeftDown extends Diagonal(MoveLeft, MoveDown)

case object MoveLeftUp extends Diagonal(MoveLeft, MoveUp)

case object MoveRightDown extends Diagonal(MoveRight, MoveDown)

case object MoveRightUp extends Diagonal(MoveRight, MoveUp)

MoveLeftDown.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))
MoveLeftUp.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))
MoveRightDown.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))
MoveRightUp.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))





