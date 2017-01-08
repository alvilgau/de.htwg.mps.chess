package core.scala.de.htwg.mps.chess.aview.gui

import akka.actor.ActorSelection
import core.scala.de.htwg.mps.chess.controller.{QuitCmd, RestartCmd, UpdateInfo}

import scala.swing._
import scala.swing.event.Key

class SwingFrame(controller: ActorSelection) extends Frame {

  menuBar = new MenuBar {
    contents += new Menu("Game") {
      mnemonic = Key.G
      contents += new MenuItem(Action("New") {
        controller ! RestartCmd
      })
      contents += new MenuItem(Action("Quit") {
        controller ! QuitCmd
      })
    }
  }

  val statusPanel = new StatusPanel()

  contents = new BorderPanel {
    layout(statusPanel) = BorderPanel.Position.North
  }

  title = "Chess"
  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
  size = new Dimension(500, 500)
  visible = true

  def update(info: UpdateInfo): Unit = {
    statusPanel.setStatus(info.status, info.checkMate.getStatusMessage)
    statusPanel.setTurn(info.turnMessage)
  }
}
