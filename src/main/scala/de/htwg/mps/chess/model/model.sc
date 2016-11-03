import de.htwg.mps.chess.controller.ChessController
import de.htwg.mps.chess.model._

val pawn = new Pawn(1, 1, Team.black, 1)
val controller = new ChessController()
controller.board.getField(1, 1).figure = Some(pawn)
println(controller.boardToString)

