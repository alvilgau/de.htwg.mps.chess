package models

import core.scala.de.htwg.mps.chess.controller.{ExchangeInfo, GameoverInfo, Info, UpdateInfo}
import core.scala.de.htwg.mps.chess.model.Board
import play.api.libs.json.{Json, OFormat}

case class FieldDTO(posX: Int, posY: Int, figure: String, var highlight: String = "")

case class InfoDTO(fields: List[FieldDTO], statusMessage: String, turnMessage: String, checkmateMessage: String)

object InfoDTO {
  def fromInfo(info: Info): InfoDTO = {
    val fields: List[FieldDTO] = fromBoard(info.board)
    info match {
      case gi: GameoverInfo => InfoDTO(fields, gi.status, "", gi.checkMate.getStatusMessage)
      case _: ExchangeInfo => InfoDTO(fields, "", "", "")
      case ui: UpdateInfo =>
        if (ui.selected != null) {
          // set selected field
          fields.find(f => f.posX == ui.selected._1 && f.posY == ui.selected._2).get.highlight = "selected "

          // set fields on the can be moved
          fields.filter(f => ui.possibleMoves.exists(p => p.posX == f.posX && p.posY == f.posY))
            .foreach(_.highlight += "possible")
        }
        InfoDTO(fields, ui.status, ui.turnMessage, ui.checkMate.getStatusMessage)
    }
  }

  private def fromBoard(board: Board): List[FieldDTO] = {
    board.fields.map(field => {
      var figure = "empty"
      if (field.isSet)
        figure = field.figure.get.team + field.figure.get.getClass.getSimpleName
      FieldDTO(field.posX, field.posY, figure)
    }).sortWith(_.posX < _.posX)
      .sortWith(_.posY > _.posY)
  }

  implicit val fieldDTOFormat: OFormat[FieldDTO] = Json.format[FieldDTO]
  implicit val infoDTOFormat: OFormat[InfoDTO] = Json.format[InfoDTO]
}
