import de.htwg.mps.chess.model.{Field, Pawn, Team}

//val fields: Array[Array[Field]] = Array.fill[Field](5, 5)(new Field(-1, -1))
//for (elem <- fields) {
//  for (elem <- elem) {
//    println(elem)
//  }
//}


val fields = Array.ofDim[Field](8, 8)

for {
  i <- 0 until fields.length
  j <- 0 until fields(0).length
} fields(i)(j) = new Field(new Pawn(2, 2, Team.black))

val x = Array(Array(1,1), Array(0,1))

val f = fields(0)(0).figure.get
val pm = f.simpleMoveValidation(f, fields, x)
pm.foreach(println)


f.verticalMoveValidation(f, fields)
