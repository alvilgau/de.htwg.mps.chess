package models

import akka.actor.{Actor, ActorRef, Props}

class Player {

  class PlayerActor(out: ActorRef) extends Actor {
    def receive: Receive = {
      case msg: String =>
        out ! msg
    }
  }

  var client: ActorRef = _

  var game: GameInstance = _

  def createActor(out: ActorRef): Props = {
    client = out
    Props(new PlayerActor(out))
  }

}
