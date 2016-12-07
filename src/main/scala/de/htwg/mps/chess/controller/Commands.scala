package de.htwg.mps.chess.controller

import de.htwg.mps.chess.controller.Exchange.ExchangeValue

trait Command {}

case class MoveCmd(posX: Int, posY: Int) extends Command

case class ExchangeCmd(exchangeValue: ExchangeValue)

case object RestartCmd extends Command

case object QuitCmd extends Command
