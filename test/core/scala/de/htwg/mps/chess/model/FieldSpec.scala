package core.scala.de.htwg.mps.chess.model

import org.scalatest.{Matchers, WordSpec}

class FieldSpec extends WordSpec with Matchers {

  "A new default Field" should {
    val field = new Field(0, 0)

    // call unapply to get 100% coverage for case classes
    Field.unapply(field)

    "be empty" in {
      field.isSet should be(false)
      field.toString should be("-")
    }
  }

  "A new Field with a Figure" should {
    val pawn = Pawn(1, 1, Team.white)
    val field = new Field(pawn)

    "not be empty" in {
      field.isSet should be(true)
      field.toString should be("P")
    }

    "be empty after clearing field" in {
      field.clear()
      field.isSet should be(false)
    }
  }
}
