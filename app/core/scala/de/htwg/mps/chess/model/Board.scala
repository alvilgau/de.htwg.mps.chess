package core.scala.de.htwg.mps.chess.model

import core.scala.de.htwg.mps.chess.model.Team.Team

import scala.collection.mutable

object Board {
  val MIN_POS: Int = 0
  var MAX_POS: Int = -1
}

case class Board(size: Int) {

  Board.MAX_POS = size - 1
  var fields: List[Field] = List()

  init()

  def init(): Unit = {
    // clear and create fields
    fields = List()
    for {
      i <- Board.MIN_POS until size
      j <- Board.MIN_POS until size
    } {
      fields = fields :+ new Field(i, j)
    }
  }

  def getFieldOption(x: Int, y: Int): Option[Field] = fields.find(f => f.posX == x && f.posY == y)

  def getField(x: Int, y: Int): Field = getFieldOption(x, y).get

  def getFields(y: Int): List[Field] = fields.filter(_.posY == y)

  def setFigure(figure: Figure): Unit = getField(figure.posX, figure.posY).figure = Some(figure)

  def getFigures(team: Team): List[Figure] = fields.filter(f => f.isSet && f.figure.get.team == team).map(_.figure.get)

  override def toString: String = {
    val sb = new mutable.StringBuilder

    sb ++= "\n |  a  b  c  d  e  f  g  h  |"
    sb ++= "\n-+--------------------------+"

    for (y <- Board.MAX_POS to Board.MIN_POS by -1) {
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