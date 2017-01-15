package core.scala.de.htwg.mps.chess.controller

import akka.actor.{Actor, ActorSystem, Props}
import core.scala.de.htwg.mps.chess.controller.Exchange.ExchangeValue
import org.scalatest.{Matchers, WordSpec}


class ChessControllerSpec extends WordSpec with Matchers {

  class DummyUI extends Actor {
    override def receive: Receive = {
      case info: Info => lastInfo = info
    }
  }

  var lastInfo: Info = _

  "A new chess controller created within an actor system" should {
    val system: ActorSystem = ActorSystem("ChessTestSystem")
    system.actorOf(Props(new DummyUI), name = "view$dummy")
    val controller = system.actorOf(Props[ChessController], "controller")

    MoveCmd.unapply(MoveCmd(1, 1))
    ExchangeCmd.unapply(ExchangeCmd(Exchange.knight))

    "already received an update info" in {
      lastInfo.isInstanceOf[UpdateInfo] should be(true)
      UpdateInfo.unapply(lastInfo.asInstanceOf[UpdateInfo])
    }

    "send invalid info" in {
      controller ! "invalid"
      Thread.sleep(200) // wait for actor message receive
      lastInfo.isInstanceOf[InvalidInfo] should be(true)
      InvalidInfo.unapply(lastInfo.asInstanceOf[InvalidInfo])
    }

    "invalid selection" in {
      controller ! MoveCmd(0, 3)
      Thread.sleep(200) // wait for actor message receive
      lastInfo match {
        case ui: UpdateInfo => ui.selected should be(null)
      }
    }

    "invalid movement" in {
      // select
      controller ! MoveCmd(0, 1)
      Thread.sleep(200) // wait for actor message receive
      lastInfo match {
        case ui: UpdateInfo => ui.selected should be((0, 1))
      }
      // move
      controller ! MoveCmd(0, 4)
      Thread.sleep(200) // wait for actor message receive
      lastInfo match {
        case ui: UpdateInfo =>
          ui.selected should be(null)
          ui.board.getField(0, 1).isSet should be(true)
      }
    }

    "select figure" in {
      controller ! MoveCmd(0, 1)
      Thread.sleep(200) // wait for actor message receive
      lastInfo match {
        case ui: UpdateInfo => ui.selected should be((0, 1))
      }
    }

    "move figure" in {
      controller ! MoveCmd(0, 3)
      Thread.sleep(200) // wait for actor message receive
      lastInfo match {
        case ui: UpdateInfo =>
          ui.selected should be(null)
          ui.board.getField(0, 3).isSet should be(true)
      }
    }

    "handle exchange" in {
      controller ! MoveCmd(1, 6)
      controller ! MoveCmd(1, 4)
      controller ! MoveCmd(0, 3)
      controller ! MoveCmd(1, 4)
      controller ! MoveCmd(5, 6)
      controller ! MoveCmd(5, 4)
      controller ! MoveCmd(1, 4)
      controller ! MoveCmd(1, 5)
      controller ! MoveCmd(0, 6)
      controller ! MoveCmd(0, 5)
      controller ! MoveCmd(1, 5)
      controller ! MoveCmd(1, 6)
      controller ! MoveCmd(0, 5)
      controller ! MoveCmd(0, 4)
      controller ! MoveCmd(1, 6)
      controller ! MoveCmd(0, 7)
      Thread.sleep(200) // wait for actor message receive
      lastInfo.isInstanceOf[ExchangeInfo] should be(true)
      ExchangeInfo.unapply(lastInfo.asInstanceOf[ExchangeInfo])
      ExchangeValue.unapply(Exchange.knight)

      controller ! ExchangeCmd(Exchange.queen)
      Thread.sleep(200) // wait for actor message receive
    }

    "handle check mate" in {
      controller ! MoveCmd(0, 4)
      controller ! MoveCmd(0, 3)
      controller ! MoveCmd(4, 1)
      controller ! MoveCmd(4, 3)
      controller ! MoveCmd(0, 3)
      controller ! MoveCmd(0, 2)
      controller ! MoveCmd(3, 0)
      controller ! MoveCmd(7, 4)
      Thread.sleep(200) // wait for actor message receive
      //      lastInfo match {
      //        case ui: UpdateInfo =>
      //          ui.checkMate.isCheckBlack should be(true)
      //      }
    }

    "restart game" in {
      controller ! RestartCmd
      Thread.sleep(200) // wait for actor message receive
      lastInfo match {
        case ui: UpdateInfo =>
          ui.selected should be(null)
          ui.board.getField(0, 1).isSet should be(true)
      }
    }

    "handle game over" in {
      controller ! MoveCmd(4, 1)
      controller ! MoveCmd(4, 3)
      controller ! MoveCmd(4, 6)
      controller ! MoveCmd(4, 4)
      controller ! MoveCmd(3, 0)
      controller ! MoveCmd(7, 4)
      controller ! MoveCmd(5, 6)
      controller ! MoveCmd(5, 5)
      Thread.sleep(200) // wait for actor message receive
      lastInfo match {
        case gi: GameoverInfo =>
          gi.checkMate.isMateBlack should be(true)
          gi.checkMate.getStatusMessage.startsWith(" Black King is mate.") should be(true)
          GameoverInfo.unapply(gi)
      }
    }

  }
}
