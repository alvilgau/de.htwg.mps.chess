package de.htwg.mps.chess.aview.tui

import akka.actor.{Actor, ActorSelection}
import de.htwg.mps.chess.controller.{Exchange, _}

private object PositionX extends Enumeration {
  type PositionX = Value
  val a, b, c, d, e, f, g, h = Value
}

class TextUI() extends Actor {

  val controller: ActorSelection = context.system.actorSelection("user/controller")

  var currentInfo: Info = _

  override def receive: Receive = {
    case info: Info =>
      currentInfo = info
      println(info.board)
      info match {
        case info: ExchangeInfo => printExchange(info)
        case info: GameoverInfo => printGameover(info)
        case info: UpdateInfo => printUpdate(info)
      }
    // cmd input
    case input: String => processInputLine(input)
  }

  private def printExchange(info: ExchangeInfo) = {
    println("Pawn reaches end of the playground. Please choose a new Figure for the exchange")
    println("Possible commands are: " + Exchange.values.mkString(", "))
  }

  private def printGameover(info: GameoverInfo) = {
    println(info.status)
    println(info.checkMate.getStatusMessage)
    println("Please enter a command: q - quit, r - restart")
  }

  private def printUpdate(info: UpdateInfo) = {
    println(info.status)
    println(info.turnMessage)
    println(info.checkMate.getStatusMessage)
    println("Please enter a command: q - quit, r - restart, ma1 - selects the figure at position a1 " +
      "OR moves the selected figure to a1")
  }

  private def processInputLine(input: String): Unit = {
    (input.toLowerCase, currentInfo) match {
      case ("q", _) => controller ! QuitCmd
      case ("r", _) => controller ! RestartCmd
      case (_, _: GameoverInfo) => println("Invalid command!")
      case (cmd, _: ExchangeInfo) => handleExchange(cmd)
      case (cmd, _) if cmd.startsWith("m") && cmd.length == 3 => handleMovement(cmd)
      case _ => println("Invalid command!")
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

  private def handleMovement(cmd: String) = {
    try {
      val posX = PositionX.withName(cmd.charAt(1).toString).id
      val posY = cmd.charAt(2).toString.toInt - 1
      controller ! MoveCmd(posX, posY)
    } catch {
      case _: NoSuchElementException => println("Invalid command!")
    }

  }
}
