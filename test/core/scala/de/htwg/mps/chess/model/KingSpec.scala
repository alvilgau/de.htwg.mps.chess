package core.scala.de.htwg.mps.chess.model

import org.scalatest.{Matchers, WordSpec}

class KingSpec extends WordSpec with Matchers {

  val board = Board(5)

  "A new King on board at position (2,2)" should {
    val king = King(2, 2, Team.white)
    King.unapply(king)

    "be a king" in {
      king.isKing should be(true)
      king.toString should be("Ö")
    }

    board.setFigure(king)
    val moves: List[(Int, Int)] = king.getPossibleMoves(board)
      .map(field => (field.posX, field.posY))

    "be able to move vertically, horizontally, diagonally with range of 1" in {
      val correctMoves = List((3, 3), (1, 1), (1, 3), (3, 1), (1, 2), (2, 3))
      val wrongMoves = List((1, 0), (2, 4))
      correctMoves.forall(moves.contains) should be(true)
      wrongMoves.forall(moves.contains) should be(false)
    }
  }

}
