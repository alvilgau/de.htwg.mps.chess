package models

import akka.actor.{Actor, ActorRef, Props}

class Player {

  class PlayerActor(out: ActorRef) extends Actor {
    def receive: Receive = {
      case msg: String =>
        out ! msg
    }

    override def postStop(): Unit = {
      if (game != null) {
        game.leave(Player.this)
      }
    }
  }

  var client: ActorRef = _

  var game: GameInstance = _

  def createActor(out: ActorRef): Props = {
    client = out
    Props(new PlayerActor(out))
  }

  def clear(): Unit = {
    game = null
  }

}
