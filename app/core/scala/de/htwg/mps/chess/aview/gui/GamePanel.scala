package core.scala.de.htwg.mps.chess.aview.gui

import java.awt.{Color, Insets}

import scala.swing.{Button, GridPanel, Label}

class GamePanel extends GridPanel(0, 9) {

  private val cols = Array("A", "B", "C", "D", "E", "F", "G", "H")
  private val light = Color.decode("#E5CEA4")
  private val dark = Color.decode("#A4785B")
  private val lightBlue = Color.decode("#B8CFE5")

  var chessFields: Array[Array[Button]] = Array.ofDim[Button](8, 8)

  // add column descriptions
  contents += new Label
  for (column <- chessFields.indices) {
    contents += new Label {
      text = cols(column)
    }
  }

  // create and add buttons
  for {
    column <- chessFields.indices
    row <- chessFields.indices
  } {
    // create button
    val button = new Button() {
      margin = new Insets(0, 0, 0, 0)
    }
    chessFields(row)(column) = button

    // add button
    if (row == 0) {
      contents += new Label {
        text = (column + 1).toString
      }
    }
    contents += button
  }

}
