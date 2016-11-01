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
}


