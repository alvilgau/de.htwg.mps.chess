package de.htwg.mps.chess.model

import scala.collection.mutable

object Board {
  val MIN_POS = 0
}

case class Board(size: Int) {

  var fields: List[Field] = List()

  /* Create fields */
  for {
    i <- Board.MIN_POS until size
    j <- Board.MIN_POS until size
  } {
    fields = fields :+ new Field(i, j)
  }

  def getFieldOption(x: Int, y: Int) = fields.find(f => f.posX == x && f.posY == y)

  def getField(x: Int, y: Int) = getFieldOption(x, y).get

  def getFields(y: Int) = fields.filter(_.posY == y)

  def setFigure(figure: Figure) = getField(figure.posX, figure.posY).figure = Some(figure)

  override def toString = {
    val sb = new mutable.StringBuilder

    sb ++= "\n |  a  b  c  d  e  f  g  h  |"
    sb ++= "\n-+--------------------------+"

    for (y <- size - 1 to Board.MIN_POS by -1) {
      sb ++= "\n" + (y + 1) + "|  "
      for (x <- Board.MIN_POS until size) {
        sb ++= getField(x, y) + "  "
      }
      sb ++= "|"
    }

    sb ++= "\n-+--------------------------+"
    sb.toString
  }
}