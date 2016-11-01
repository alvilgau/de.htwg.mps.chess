import de.htwg.mps.chess.model._


val b = new Board(3, 3)
val k = new Pawn(1, 1, Team.white, 1)
b.getFieldOption(1, 1).get.figure = Some(k)

k.diagonalMoveValidation(k, b).foreach(println)

k.verticalMoveValidation(k, b).foreach(println)

k.horizontalMoveValidation(k, b).foreach(println)

k.getPossibleMoves(b).foreach(println)


