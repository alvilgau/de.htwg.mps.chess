package core.scala.de.htwg.mps.chess.aview.gui

import akka.actor.{Actor, ActorSelection}
import core.scala.de.htwg.mps.chess.controller.{InvalidInfo, UpdateInfo}

class SwingActor extends Actor {
  val controller: ActorSelection = context.system.actorSelection("user/controller")
  val frame = new SwingFrame(controller)

  override def receive: Receive = {
    case info: InvalidInfo => println(info.message)
    //    case info: ExchangeInfo => printExchange(info)
    //    case info: GameoverInfo => printGameover(info)
    case info: UpdateInfo => frame.update(info)
  }
}
