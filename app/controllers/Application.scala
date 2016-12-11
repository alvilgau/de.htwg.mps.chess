package controllers

import java.util.UUID

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.google.inject.Inject
import models.Player
import play.api.libs.streams.ActorFlow
import play.api.mvc._

import scala.collection.mutable

class Application @Inject()(implicit system: ActorSystem, materializer: Materializer) extends Controller {

  private val SESSION_PLAYER_ID = "PLAYER_ID"
  private val players = new mutable.HashMap[String, Player]()

  def index = Action {
    Ok(views.html.index())
  }

  def rules = Action {
    Ok(views.html.rules())
  }

  private def getCurrentPlayer(request: RequestHeader) = {
    val id: Option[String] = request.session.get(SESSION_PLAYER_ID)
    players.get(id.orNull)
  }

  def lobby = Action { request =>
    val player = getCurrentPlayer(request)
    if (player.isEmpty) {
      // first visit, create player
      val playerId = UUID.randomUUID().toString
      players.put(playerId, new Player())
      Ok(views.html.lobby()).withSession(SESSION_PLAYER_ID -> playerId)
    }
    else {
      Ok(views.html.lobby())
    }
  }

  def socket(): WebSocket = WebSocket.accept[String, String] { request =>
    val player = getCurrentPlayer(request)
    ActorFlow.actorRef(out => player.get.createActor(out))
  }

}