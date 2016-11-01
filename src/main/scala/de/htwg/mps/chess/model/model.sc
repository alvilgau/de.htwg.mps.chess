import de.htwg.mps.chess.model._


val f = new Field(1, 1)
println(f.figure)

val f2 = new King(1, 2, Team.black)
val f3 = new Pawn(1, 2, Team.black)
val f4 = new Field(f2)

println(f4.isKing())
f4.figure = Some(f3)
println(f4.isKing())


f4.clear()
println(f4.isKing())


