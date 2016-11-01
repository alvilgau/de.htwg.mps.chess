package de.htwg.mps.chess.model

import de.htwg.mps.chess.model.Team.Team

case class Pawn(var posX: Int, var posY: Int, var team: Team) extends Figure {

}
