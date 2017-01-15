package core.scala.de.htwg.mps.chess.model

import org.scalatest.{Matchers, WordSpec}

class QueenSpec extends WordSpec with Matchers {

  val board = Board(5)

  "A new Queen on board at position (2,2)" should {
    val queen = Queen(2, 2, Team.white)
    // call unapply to get 100% coverage for case classes
    Queen.unapply(queen)

    board.setFigure(queen)
    val moves: List[(Int, Int)] = queen.getPossibleMoves(board)
      .map(field => (field.posX, field.posY))

    "be able to move vertically, horizontally, diagonally" in {
      val correctMoves = List((3, 3), (1, 1), (1, 3), (3, 1), (1, 2), (2, 3))
      correctMoves.forall(moves.contains) should be(true)
    }

  }

}
