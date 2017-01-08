package core.scala.de.htwg.mps.chess.aview.gui

import java.awt.{Color, Font}
import javax.swing.BorderFactory

import scala.swing.{GridPanel, Label}

class StatusPanel extends GridPanel(2, 1) {

  val arialFont = new Font("Arial", Font.CENTER_BASELINE, 16)
  background = Color.WHITE
  border = BorderFactory.createLineBorder(Color.BLACK, 1)

  val status = new Label {
    font = arialFont
  }
  contents += status

  val turn = new Label {
    font = arialFont
  }
  contents += turn

  def setStatus(game: String, checkmate: String): Unit = status.text = "Status: " + game + " " + checkmate

  def setTurn(turn: String): Unit = this.turn.text = "Turn: " + turn
}
