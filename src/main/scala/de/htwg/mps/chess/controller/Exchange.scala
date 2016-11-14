package de.htwg.mps.chess.controller

import de.htwg.mps.chess.model.{Bishop, Knight, Queen, Rook}

object Exchange extends Enumeration {

  case class ExchangeValue(clazz: Class[_]) extends Val {}

  type Exchange = Value
  val knight = ExchangeValue(classOf[Knight])
  val bishop = ExchangeValue(classOf[Bishop])
  val rook = ExchangeValue(classOf[Rook])
  val queen = ExchangeValue(classOf[Queen])
}