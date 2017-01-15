package core.scala.de.htwg.mps.chess.model

import core.scala.de.htwg.mps.chess.model.movement.MoveComplex
import org.scalatest.{Matchers, WordSpec}

class KnightSpec extends WordSpec with Matchers {

  val board = Board(5)

  "A new Knight on board at position (2,2)" should {
    val knight = Knight(2, 2, Team.white)
    // call unapply to get 100% coverage for case classes
    Knight.unapply(knight)
    MoveComplex.unapply(MoveComplex(2, 1))

    board.setFigure(knight)
    val moves: List[(Int, Int)] = knight.getPossibleMoves(board)
      .map(field => (field.posX, field.posY))

    "be able to move 2 steps right/left and 1 step up/down" in {
      val correctMoves = List((0, 3), (0, 1), (4, 1), (4, 3))
      correctMoves.forall(moves.contains) should be(true)
    }

    "be able to move 1 step right/left and 2 step up/down" in {
      val correctMoves = List((1, 4), (3, 4), (3, 0), (1, 0))
      correctMoves.forall(moves.contains) should be(true)
    }

  }

}
