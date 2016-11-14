package de.htwg.mps.chess.aview.tui

import de.htwg.mps.chess.controller.Exchange.ExchangeValue
import de.htwg.mps.chess.controller.{ChessController, Exchange}
import de.htwg.mps.chess.util.Observer

private object PositionX extends Enumeration {
  type PositionX = Value
  val a, b, c, d, e, f, g, h = Value
}

class TextUI(controller: ChessController) extends Observer {
  controller.add(this)
  printTUI()

  private var continue = true

  override def update() = printTUI()

  def printTUI() = {
    println(controller.board)

    if (controller.exchange) {
      println("Pawn reaches end of the playground. Please choose a new Figure for the exchange")
      println("Possible commands are: " + Exchange.values.mkString(", "))
    }
    else {
      println(controller.status)
      println(controller.getTurnMessage)
      println("Please enter a command: q - quit, r - restart, ma1 - selects the figure at position a1 " +
        "OR moves the selected figure to a1")
    }
  }

  def processInputLine(input: String) = {
    input.toLowerCase match {
      case "q" => continue = false
      case "r" => println("restart")
      case cmd if controller.exchange => handleExchange(cmd)
      case cmd if cmd.startsWith("m") && cmd.length == 3 => handleMovement(cmd)
      case _ => println("Invalid command!")
    }

    continue
  }

  private def handleExchange(cmd: String) = {
    try {
      val exchangeVal = Exchange.withName(cmd).asInstanceOf[ExchangeValue]
      controller.doExchange(exchangeVal)
    }
    catch {
      case e: NoSuchElementException => println("Invalid command!")
    }
  }

  private def handleMovement(cmd: String) = {
    try {
      val posX = PositionX.withName(cmd.charAt(1).toString).id
      val posY = cmd.charAt(2).toString.toInt - 1
      controller.handleMovement(posX, posY)
    } catch {
      case e: NoSuchElementException => println("Invalid command!")
    }

  }
}
