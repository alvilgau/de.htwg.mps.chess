package core.scala.de.htwg.mps.chess.model

import org.scalatest.{Matchers, WordSpec}

class PawnSpec extends WordSpec with Matchers {

  val board = Board(5)

  "A new white Pawn on board at position (2,2)" should {
    val pawn = Pawn(2, 2, Team.white)
    // call unapply to get 100% coverage for case classes
    Pawn.unapply(pawn)
    board.setFigure(pawn)

    "be able to move till to two steps forward" in {
      val moves: List[(Int, Int)] = pawn.getPossibleMoves(board)
        .map(field => (field.posX, field.posY))
      val correctMoves = List((2, 3), (2, 4))
      val wrongMoves = List((2, 5), (2, 1))
      correctMoves.forall(moves.contains) should be(true)
      wrongMoves.forall(moves.contains) should be(false)
    }


    "be able to move one step forward after moving" in {
      pawn.move(2, 3)
      val moves: List[(Int, Int)] = pawn.getPossibleMoves(board)
        .map(field => (field.posX, field.posY))
      moves.contains((2, 4)) should be(true)
      moves.contains((2, 5)) should be(false)
    }
  }

  "A new black Pawn" should {
    val pawn = Pawn(4, 4, Team.black)

    "be able to move downwards" in {
      pawn.posY should be(4)
      pawn.move(4, 3)
      pawn.posY should be(3)
    }
  }

}
