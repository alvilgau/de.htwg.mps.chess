package de.htwg.mps.chess.model

import de.htwg.mps.chess.model.Team.Team

abstract class Figure {

  var posX: Int

  var posY: Int

  var team: Team

  def move(x: Int, y: Int): Boolean = {
    posX = x
    posY = y
    return false
  }

}
