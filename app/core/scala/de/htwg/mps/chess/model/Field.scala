package core.scala.de.htwg.mps.chess.model

case class Field(var figure: Option[Figure], posX: Int, posY: Int) {

  def this(x: Int, y: Int) = this(None, x, y)

  def this(figure: Figure) = this(Some(figure), figure.posX, figure.posY)

  def isSet: Boolean = figure.isDefined

  def clear(): Unit = figure = None

  override def toString: String = {
    if (isSet) {
      figure.get.toString
    } else {
      "-"
    }
  }
}
