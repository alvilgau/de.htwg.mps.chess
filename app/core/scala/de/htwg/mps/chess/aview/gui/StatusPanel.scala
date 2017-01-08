package core.scala.de.htwg.mps.chess.aview.gui

import java.awt.Color
import javax.swing.BorderFactory

import scala.swing.{GridPanel, Label}

class StatusPanel extends GridPanel(2, 1) {

  background = Color.WHITE
  border = BorderFactory.createLineBorder(Color.BLACK, 1)

  val status = new Label
  contents += status

  val turn = new Label
  contents += turn

  def setStatus(game: String, checkmate: String): Unit = status.text = "Status: " + game + " " + checkmate

  def setTurn(turn: String): Unit = this.turn.text = "Turn: " + turn
}
