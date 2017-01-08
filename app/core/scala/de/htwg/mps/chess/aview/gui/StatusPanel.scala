package core.scala.de.htwg.mps.chess.aview.gui

import scala.swing.{GridPanel, Label}

class StatusPanel extends GridPanel(2, 1) {

  val status = new Label {
    text = "Status:"
  }

  val turn = new Label {
    text = "Turn:"
  }

  contents += status
  contents += turn

  def setStatus(game: String, checkmate: String): Unit = status.text = "Status: " + game + " " + checkmate

  def setTurn(turn: String): Unit = this.turn.text = "Turn: " + turn
}
