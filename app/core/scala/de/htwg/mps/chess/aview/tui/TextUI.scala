package core.scala.de.htwg.mps.chess.aview.tui

import akka.actor.{Actor, ActorSelection}
import core.scala.de.htwg.mps.chess.controller._
import core.scala.de.htwg.mps.chess.model.Board

import scala.collection.mutable

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
    printBoard(info.board)
    println("Pawn reaches end of the playground. Please choose a new Figure for the exchange")
    println("Possible commands are: " + Exchange.values.mkString(", "))
  }

  private def printGameover(info: GameoverInfo) = {
    printBoard(info.board)
    println(info.status)
    println(info.checkMateMessage)
    println("Please enter a command: q - quit, r - restart")
  }

  private def printUpdate(info: UpdateInfo) = {
    printBoard(info.board)
    println(info.status)
    println(info.turnMessage)
    println(info.checkMateMessage)
    println("Please enter a command: q - quit, r - restart, ma1 - selects the figure at position a1 " +
      "OR moves the selected figure to a1")
  }

  private def printBoard(board: Board): Unit = {
    val sb = new mutable.StringBuilder

    sb ++= "\n |  a  b  c  d  e  f  g  h  |"
    sb ++= "\n-+--------------------------+"

    for (y <- Board.MAX_POS to Board.MIN_POS by -1) {
      sb ++= "\n" + (y + 1) + "|  "
      for (x <- Board.MIN_POS until board.size) {
        sb ++= board.getField(x, y) + "  "
      }
      sb ++= "|"
    }

    sb ++= "\n-+--------------------------+"
    println(sb)
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
