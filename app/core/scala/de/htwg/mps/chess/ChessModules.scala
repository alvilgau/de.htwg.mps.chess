package core.scala.de.htwg.mps.chess

import akka.actor.{ActorRef, ActorSystem, Props}
import core.scala.de.htwg.mps.chess.aview.gui.SwingActor
import core.scala.de.htwg.mps.chess.aview.tui.TextUI

trait ChessModule {
  def createUI()(implicit system: ActorSystem): Unit

  def createTUI()(implicit system: ActorSystem): ActorRef = system.actorOf(Props[TextUI], "view$tui")

  def createGUI()(implicit system: ActorSystem): ActorRef = system.actorOf(Props[SwingActor], "view$gui")
}

trait OfflineModule extends ChessModule {
  var tui: ActorRef = _
  var gui: ActorRef = _

  override def createUI()(implicit system: ActorSystem): Unit = {
    tui = createTUI()
    gui = createGUI()
  }
}

trait WebModule extends ChessModule {
  override def createUI()(implicit system: ActorSystem): Unit = {
    createTUI()
  }
}
