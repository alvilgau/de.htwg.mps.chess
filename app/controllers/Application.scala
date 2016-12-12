package controllers

import java.util.UUID

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.google.inject.Inject
import core.scala.de.htwg.mps.chess.controller.Exchange.ExchangeValue
import core.scala.de.htwg.mps.chess.controller.{Exchange, ExchangeCmd, MoveCmd}
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
    gameInstances = gameInstances.filter(!_._2.finished)
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

  def joinGame(id: String) = Action { request =>
    val gameInstance = gameInstances.get(id)
    val player = getCurrentPlayer(request)
    gameInstance.get.join(player.get)
    Ok(views.html.chess())
  }

  def chess() = Action {
    Ok(views.html.chess())
  }

  def move(posX: Int, posY: Int) = Action { request =>
    val player = getCurrentPlayer(request).get
    val game = player.game
    if (game.isCurrentTurn(player)) {
      game.chess.controller ! MoveCmd(posX, posY)
      Ok("successfully move")
    } else {
      Ok("not your turn")
    }
  }

  def exchange(figure: String) = Action { request =>
    val exchangeValue = Exchange.withName(figure).asInstanceOf[ExchangeValue]
    getCurrentPlayer(request).get.game.chess.controller ! ExchangeCmd(exchangeValue)
    Ok("exchanged")
  }
}