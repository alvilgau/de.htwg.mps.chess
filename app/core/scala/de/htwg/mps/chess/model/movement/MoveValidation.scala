package core.scala.de.htwg.mps.chess.model.movement

import core.scala.de.htwg.mps.chess.model._

trait MoveValidation {
  def validate(fields: List[Field])(implicit figure: Figure): List[Field]
}

/**
  * The Default validation.
  * Figure can move on a field when it is empty or is taken by a enemy figure
  */
object ValidationWithKill extends MoveValidation {
  override def validate(fields: List[Field])(implicit figure: Figure): List[Field] = {
    val index = fields.indexWhere(_.isSet)
    if (index == -1) {
      return fields
    }
    val list = fields.take(index + 1)
    if (list.last.figure.get.team == figure.team) {
      return list.dropRight(1)
    }
    list
  }
}

/**
  * Figure can only move on empty fields.
  */
object ValidationWithoutKill extends MoveValidation {
  override def validate(fields: List[Field])(implicit figure: Figure): List[Field] = {
    fields.takeWhile(!_.isSet)
  }
}

/**
  * Figure can only move one field where a enemy figure is set.
  */
object ValidationKill extends MoveValidation {
  override def validate(fields: List[Field])(implicit figure: Figure): List[Field] = {
    if (fields.isEmpty) {
      return fields
    }

    val fistField = fields.head
    if (fistField.isSet && fistField.figure.get.team != figure.team) {
      return List(fistField)
    }
    List()
  }
}
