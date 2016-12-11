package models

import core.scala.de.htwg.mps.chess.model.Field
import play.api.libs.json.{Json, OFormat}

case class FieldDTO(posX: Int, posY: Int, figure: String)

object FieldDTO {

  def fromField(field: Field): FieldDTO = {
    var figure: String = "empty"
    if (field.isSet)
      figure = field.figure.get.team + field.figure.get.getClass.getSimpleName
    FieldDTO(field.posX, field.posY, figure)
  }

  implicit val fieldDTOFormat: OFormat[FieldDTO] = Json.format[FieldDTO]
}
