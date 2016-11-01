import de.htwg.mps.chess.model.{Pawn, Team}

val f = Pawn(1, 2, Team.black)
println(f.posX)
f.move(3, 3)
println(f.posX)
