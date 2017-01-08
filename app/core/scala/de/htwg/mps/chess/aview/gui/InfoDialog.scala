package core.scala.de.htwg.mps.chess.aview.gui

import javax.swing.{Icon, JOptionPane, UIManager}

import core.scala.de.htwg.mps.chess.controller.Exchange
import core.scala.de.htwg.mps.chess.controller.Exchange.ExchangeValue

import scala.swing.Swing.EmptyIcon
import scala.swing.{Component, Dialog, Swing}

object InfoDialog {

  private def showOptions[A <: Enumeration](
                                             parent: Component = null,
                                             message: Any,
                                             title: String = UIManager.getString("OptionPane.titleText"),
                                             messageType: Dialog.Message.Value = Dialog.Message.Question,
                                             icon: Icon = EmptyIcon,
                                             entries: A,
                                             initial: A#Value): Option[A#Value] = {
    val r = JOptionPane.showOptionDialog(
      if (parent == null) null else parent.peer, message, title, 0,
      messageType.id, Swing.wrapIcon(icon),
      entries.values.toArray[AnyRef], initial)
    if (r < 0) None else Some(entries(r))
  }

  def handleExchange(frame: Component): ExchangeValue = {
    var option: Option[Exchange.Value] = None
    while (option.isEmpty) {
      option = showOptions(parent = frame,
        message = "Pawn reaches end of the playground.\nPlease select a new figure for the exchange!",
        title = "Exchange", entries = Exchange, initial = Exchange.knight)
    }
    option.get.asInstanceOf[ExchangeValue]
  }
}
