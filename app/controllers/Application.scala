package controllers

import java.util.UUID

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.google.inject.Inject
import models.{GameInstance, Player}
import play.api.libs.streams.ActorFlow
import play.api.mvc._

import scala.collection.mutable

class Application @Inject()(implicit system: ActorSystem, materializer: Materializer) extends Controller {

  private val SESSION_PLAYER_ID = "PLAYER_ID"
  private val players = new mutable.HashMap[String, Player]()
  private var gameInstances = Map[String, GameInstance]()

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
      Ok(views.html.lobby(gameInstances)).withSession(SESSION_PLAYER_ID -> playerId)
    }
    else {
      Ok(views.html.lobby(gameInstances))
    }
  }

  def socket(): WebSocket = WebSocket.accept[String, String] { request =>
    val player = getCurrentPlayer(request)
    ActorFlow.actorRef(out => player.get.createActor(out))
  }

  def createGame(gameName: String) = Action { request =>
    val player = getCurrentPlayer(request).get
    val gameInstance = new GameInstance(gameName, player)
    gameInstances = gameInstances + (gameInstance.gameId -> gameInstance)
    Ok(views.html.wait())
  }
}