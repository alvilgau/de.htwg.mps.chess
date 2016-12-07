package de.htwg.mps.chess.aview.tui

import akka.actor.{Actor, ActorSelection}
import de.htwg.mps.chess.controller.{Exchange, _}

private object PositionX extends Enumeration {
  type PositionX = Value
  val a, b, c, d, e, f, g, h = Value
}

class TextUI() extends Actor {

  private val controller: ActorSelection = context.system.actorSelection("user/controller")

  override def receive: Receive = {
    case info: InvalidInfo => println(info.message)
    case info: ExchangeInfo => printExchange(info)
    case info: GameoverInfo => printGameover(info)
    case info: UpdateInfo => printUpdate(info)

    // cmd line input
    case input: String => processInputLine(input)
  }

  private def printExchange(info: ExchangeInfo) = {
    println(info.board)
    println("Pawn reaches end of the playground. Please choose a new Figure for the exchange")
    println("Possible commands are: " + Exchange.values.mkString(", "))
  }

  private def printGameover(info: GameoverInfo) = {
    println(info.board)
    println(info.status)
    println(info.checkMate.getStatusMessage)
    println("Please enter a command: q - quit, r - restart")
  }

  private def printUpdate(info: UpdateInfo) = {
    println(info.board)
    println(info.status)
    println(info.turnMessage)
    println(info.checkMate.getStatusMessage)
    println("Please enter a command: q - quit, r - restart, ma1 - selects the figure at position a1 " +
      "OR moves the selected figure to a1")
  }

  private def processInputLine(input: String): Unit = {
    input.toLowerCase match {
      case "q" => controller ! QuitCmd
      case "r" => controller ! RestartCmd
      case cmd if cmd.startsWith("m") && cmd.length == 3 => handleMovement(cmd)
      case cmd => handleExchange(cmd)
    }
  }

  private def handleMovement(cmd: String) = {
    try {
      val posX = PositionX.withName(cmd.charAt(1).toString).id
      val posY = cmd.charAt(2).toString.toInt - 1
      controller ! MoveCmd(posX, posY)
    } catch {
      case _: NoSuchElementException => println("Invalid command!")
    }
  }

  private def handleExchange(cmd: String) = {
    try {
      val exchangeVal = Exchange.withName(cmd).asInstanceOf[Exchange.ExchangeValue]
      controller ! ExchangeCmd(exchangeVal)
    }
    catch {
      case _: NoSuchElementException => println("Invalid command!")
    }
  }

}
