import core.scala.de.htwg.mps.chess.model._

val board = new Board(8)
val fig = Pawn(3, 3, Team.white)

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

class Move(md: MoveDirection) {

  private def sameDirection(field: Field)(implicit figure: Figure): Boolean = {
    md match {
      case Left | Right => field.posY == figure.posY
      case Up | Down => field.posX == figure.posX
    }
  }

  def perform(fields: List[Field], figure: Figure): List[Field] = {
    implicit val f: Figure = figure
    fields.filter(md.filter)
      .filter(sameDirection)
      .sortWith(md.sort)
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

case object MoveLeft extends Move(Left)

case object MoveRight extends Move(Right)

case object MoveUp extends Move(Up)

case object MoveDown extends Move(Down)

MoveLeft.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))
MoveRight.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))
MoveUp.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))
MoveDown.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))


case object MoveLeftDown extends Diagonal(Left, Down)

case object MoveLeftUp extends Diagonal(Left, Up)

case object MoveRightDown extends Diagonal(Right, Down)

case object MoveRightUp extends Diagonal(Right, Up)

MoveLeftDown.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))
MoveLeftUp.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))
MoveRightDown.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))
MoveRightUp.perform(board.fields, fig).foreach(f => println((f.posX, f.posY)))





