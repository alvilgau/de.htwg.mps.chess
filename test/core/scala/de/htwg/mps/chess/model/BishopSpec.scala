package core.scala.de.htwg.mps.chess.model

import org.scalatest.{Matchers, WordSpec}

class BishopSpec extends WordSpec with Matchers {

  val board = Board(5)

  "A new Bishop on board at position (2,2)" should {
    val bishop = Bishop(2, 2, Team.white)
    // call unapply to get 100% coverage for case classes
    Bishop.unapply(bishop)

    board.setFigure(bishop)
    val moves: List[(Int, Int)] = bishop.getPossibleMoves(board)
      .map(field => (field.posX, field.posY))

    "be able to move diagonally" in {
      val correctMoves = List((3, 3), (1, 1), (1, 3), (3, 1))
      correctMoves.forall(moves.contains) should be(true)
    }

    "be not able to move vertically or horizontally" in {
      val wrongMoves = List((1, 2), (2, 3))
      wrongMoves.forall(moves.contains) should be(false)
    }
  }

}
