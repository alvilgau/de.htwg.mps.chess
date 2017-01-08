package core.scala.de.htwg.mps.chess.aview.gui

import akka.actor.ActorSelection
import core.scala.de.htwg.mps.chess.controller.Exchange.ExchangeValue
import core.scala.de.htwg.mps.chess.controller._

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

  val statusPanel = new StatusPanel
  val gamePanel = new GamePanel(controller)

  contents = new BorderPanel {
    layout(statusPanel) = BorderPanel.Position.North
    layout(gamePanel) = BorderPanel.Position.Center
  }

  title = "Chess"
  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
  size = new Dimension(500, 500)
  visible = true

  def update(info: Info): Unit = {
    gamePanel.update(info)

    info match {
      case _: ExchangeInfo =>
        val exchangeValue: ExchangeValue = InfoDialog.handleExchange(contents.head)
        controller ! ExchangeCmd(exchangeValue)
      case gi: GameoverInfo =>
        // TODO: show game over dialog/popup
        statusPanel.setStatus(gi.status, gi.checkMate.getStatusMessage)
        statusPanel.setTurn("-")
      case ui: UpdateInfo =>
        statusPanel.setStatus(ui.status, ui.checkMate.getStatusMessage)
        statusPanel.setTurn(ui.turnMessage)
    }
  }

}
