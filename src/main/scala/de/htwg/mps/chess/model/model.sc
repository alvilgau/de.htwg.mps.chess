import de.htwg.mps.chess.model.{Field, King, Pawn, Team}

//val fields: Array[Array[Field]] = Array.fill[Field](5, 5)(new Field(-1, -1))
//for (elem <- fields) {
//  for (elem <- elem) {
//    println(elem)
//  }
//}


val fields = Array.ofDim[Field](8, 8)

for {
  i <-  fields.indices
  j <-  fields(0).indices
} fields(i)(j) = new Field(new King(2, 2, Team.black))

val x = Array(Array(1,1), Array(0,1))

val f = fields(0)(0).figure.get
val pm = f.simpleMoveValidation(f, fields, x)
pm.foreach(println)

f.getPossibleMoves(fields).foreach(println)


f.verticalMoveValidation(f, fields)
