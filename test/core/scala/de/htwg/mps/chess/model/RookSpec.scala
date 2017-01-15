package core.scala.de.htwg.mps.chess.model

import org.scalatest.{Matchers, WordSpec}

class RookSpec extends WordSpec with Matchers {

  val board = Board(5)

  "A new Rook on board at position (2,2)" should {
    val rook = Rook(2, 2, Team.white)
    // call unapply to get 100% coverage for case classes
    Rook.unapply(rook)

    board.setFigure(rook)
    val moves: List[(Int, Int)] = rook.getPossibleMoves(board)
      .map(field => (field.posX, field.posY))

    "be able to move vertically" in {
      val correctMoves = List((2, 1), (2, 0), (2, 3), (2, 4))
      correctMoves.forall(moves.contains) should be(true)
    }

    "be able to move horizontally" in {
      val correctMoves = List((1, 2), (0, 2), (3, 2), (4, 2))
      correctMoves.forall(moves.contains) should be(true)
    }

    "not be able to move diagonally" in {
      val wrongMoves = List((3, 3), (1, 1))
      wrongMoves.forall(moves.contains) should be(false)
      rook.move(1, 2)
    }

  }

}
