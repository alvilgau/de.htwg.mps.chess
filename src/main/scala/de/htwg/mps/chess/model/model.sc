import de.htwg.mps.chess.controller.ChessController
import de.htwg.mps.chess.model._


val b = new Board(8, 8)
val k = new Pawn(1, 1, Team.black, 1)
b.getFieldOption(1, 1).get.figure = Some(k)

val controller = new ChessController()
println(controller)



