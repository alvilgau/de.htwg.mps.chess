package de.htwg.mps.chess.model

import scala.collection.mutable

object Board {
  val MIN_POS = 0
}

case class Board(sizeX: Int, sizeY: Int) {

  val fields = new mutable.MutableList[Field]()

  /* Initialize board */
  for {
    i <- Board.MIN_POS to sizeX
    j <- Board.MIN_POS to sizeY
  } {
    fields += new Field(i, j)
  }

  def getField(x: Int, y: Int) = fields.filter(f => f.posX == x && f.posY == y).head

  def getFieldOption(x: Int, y: Int) = fields.find(f => f.posX == x && f.posY == y)

  override def toString = {
    val sb = new mutable.StringBuilder

    sb ++= "\n |  a  b  c  d  e  f  g  h  |"
    sb ++= "\n-+--------------------------+"

    for (y <- sizeY - 1 to Board.MIN_POS by -1) {
      sb ++= "\n" + (y + 1) + "|  "
      for (x <- Board.MIN_POS until sizeX) {
        sb ++= getField(x, y) + "  "
      }
      sb ++= "|"
    }

    sb ++= "\n-+--------------------------+"
    sb.toString
  }
}