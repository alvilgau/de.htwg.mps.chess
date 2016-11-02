package de.htwg.mps.chess.model

import scala.collection.mutable

case class Board(sizeX: Int, sizeY: Int) {

  val MIN_POS = 0

  val fields = new mutable.MutableList[Field]()

  for {
    i <- MIN_POS to sizeX
    j <- MIN_POS to sizeY
  } {
    fields += new Field(i, j)
  }

  def getField(x: Int, y: Int) = fields.filter(f => f.posX == x && f.posY == y).head

  def getFieldOption(x: Int, y: Int): Option[Field] = fields.find(f => f.posX == x && f.posY == y)

  override def toString = {
    val sb = new mutable.StringBuilder

    sb ++= "\n |  a  b  c  d  e  f  g  h  |"
    sb ++= "\n-+--------------------------+"

    for (y <- sizeY - 1 to MIN_POS by -1) {
      sb ++= "\n" + (y + 1) + "|  "
      for (x <- MIN_POS until sizeX) {
        sb ++= getField(x, y) + "  "
      }
      sb ++= "|"
    }

    sb ++= "\n-+--------------------------+"
    sb.toString
  }
}


