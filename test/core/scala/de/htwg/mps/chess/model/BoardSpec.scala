package core.scala.de.htwg.mps.chess.model

import org.scalatest.{Matchers, WordSpec}

class BoardSpec extends WordSpec with Matchers {

  "A new board with size of 5" should {
    val board = new Board(5)
    Board.unapply(board)

    val pawn = Pawn(2, 2, Team.black)
    board.setFigure(pawn)

    "be empty at pos y = 1" in {
      board.getFields(1).forall(!_.isSet) should be(true)
    }

    "has 1 black figure" in {
      board.getFigures(Team.black).size should be(1)
    }
  }
}
