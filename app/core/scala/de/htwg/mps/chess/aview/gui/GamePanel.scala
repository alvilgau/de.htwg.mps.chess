package core.scala.de.htwg.mps.chess.aview.gui

import java.awt.{Color, Insets}
import javax.swing.ImageIcon

import akka.actor.ActorSelection
import core.scala.de.htwg.mps.chess.controller.{Info, MoveCmd, UpdateInfo}
import core.scala.de.htwg.mps.chess.model.Board

import scala.swing.event.ButtonClicked
import scala.swing.{Button, GridPanel, Label}

class GamePanel(controller: ActorSelection) extends GridPanel(0, 9) {

  private val cols = Array("A", "B", "C", "D", "E", "F", "G", "H")
  private val light = Color.decode("#E5CEA4")
  private val dark = Color.decode("#A4785B")
  private val lightBlue = Color.decode("#B8CFE5")
  private val imagesPath = "public/images/"

  var chessButtons: Array[Array[Button]] = Array.ofDim[Button](8, 8)

  // add column descriptions
  contents += new Label
  for (column <- chessButtons.indices) {
    contents += new Label {
      text = cols(column)
    }
  }

  // create and add buttons
  for {
    column <- chessButtons.indices.reverse
    row <- chessButtons.indices
  } {
    // create button
    val button = new Button() {
      margin = new Insets(0, 0, 0, 0)
      reactions += {
        case _: ButtonClicked => controller ! MoveCmd(row, column)
      }
    }
    chessButtons(row)(column) = button

    // add button
    if (row == 0) {
      contents += new Label {
        text = (column + 1).toString
      }
    }
    contents += button
  }

  def update(info: Info): Unit = {
    updateBoard(info.board)

    val ui = info.asInstanceOf[UpdateInfo]
    // mark selected field
    if (ui.selected != null) {
      chessButtons(ui.selected._1)(ui.selected._2).background = lightBlue
    }
    // mark possible moves
    if (ui.possibleMoves != null) {
      ui.possibleMoves.foreach { field =>
        chessButtons(field.posX)(field.posY).background = lightBlue
      }
    }
  }

  private def updateBoard(board: Board): Unit = {
    board.fields.foreach { field =>
      val button = chessButtons(field.posX)(field.posY)

      // set background color
      if ((field.posX + field.posY) % 2 == 1) {
        button.background = dark
      } else {
        button.background = light
      }

      // set figures
      val figure = field.figure
      if (figure.isEmpty) {
        button.icon = null
      } else {
        val imageName = figure.get.team + figure.get.getClass.getSimpleName + ".png"
        button.icon = new ImageIcon(imagesPath + imageName)
      }
    }
  }

}
