package core.scala.de.htwg.mps.chess.aview.gui

import akka.actor.{Actor, ActorSelection}
import core.scala.de.htwg.mps.chess.controller.{Info, InvalidInfo}

class SwingActor extends Actor {
  val controller: ActorSelection = context.system.actorSelection("user/controller")
  val frame = new SwingFrame(controller)

  override def receive: Receive = {
    case info: InvalidInfo => println(info.message)
    case info: Info => frame.update(info)
  }
}
