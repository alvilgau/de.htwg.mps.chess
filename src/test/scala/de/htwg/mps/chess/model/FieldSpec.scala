package de.htwg.mps.chess.model

import org.scalatest.{Matchers, WordSpec}

class FieldSpec extends WordSpec with Matchers {

  "A new Field without figure" should {
    val field = new Field(0, 0)

    "be not set" in {
      field.isSet shouldBe false
    }

    "have not a king" in {
      field.isKing shouldBe false
    }

    "return the string '-'" in {
      field.toString shouldBe "-"
    }
  }

  "A new Field with a pawn" should {
    val field = new Field(new Pawn(1, 1, Team.white))

    "return the string 'P'" in {
      field.toString shouldBe "P"
    }

    "not have a king" in {
      field.isKing shouldBe false
    }

    "be empty after clear" in {
      field.clear()
      field.isSet shouldBe false
    }
  }

  "A new Field with a king" should {
    val field = new Field(new King(0, 3, Team.white))

    "have a king" in {
      field.isSet shouldBe true
      field.isKing shouldBe true
    }
  }
}
