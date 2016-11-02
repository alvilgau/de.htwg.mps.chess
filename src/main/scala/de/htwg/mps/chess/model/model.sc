import de.htwg.mps.chess.model._


val b = new Board(3, 3)
val k = new Pawn(1, 1, Team.black, 1)
b.getFieldOption(1, 1).get.figure = Some(k)



