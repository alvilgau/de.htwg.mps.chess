package de.htwg.mps.chess.aview.tui

import de.htwg.mps.chess.controller.ChessController
import de.htwg.mps.chess.util.Observer

private object PositionX extends Enumeration {
  type PositionX = Value
  val a, b, c, d, e, f, g, h = Value
}

class TextUI(controller: ChessController) extends Observer {
  controller.add(this)
  printTUI()

  private var continue = true

  override def update(): Unit = printTUI()

  def printTUI() = {
    println(controller.board)
    println(controller.status)
    println(controller.getTurnMessage)
    println("Please enter a command: q - quit, r - restart, ma1 - selects the figure at position a1 " +
      "OR moves the selected figure to a1")
  }

  def processInputLine(input: String) = {
    input match {
      case "q" => continue = false
      case "r" => println("restart")
      case cmd if cmd.startsWith("m") && cmd.length == 3 => handleMovement(cmd)
      case _ => println("Invalid command!")
    }

    continue
  }

  private def handleMovement(input: String) = {
    try {
      val posX = PositionX.withName(input.charAt(1).toString).id
      val posY = input.charAt(2).toString.toInt - 1
      controller.handleMovement(posX, posY)
    } catch {
      case e: NoSuchElementException => println("Invalid command!")
    }

  }
}
