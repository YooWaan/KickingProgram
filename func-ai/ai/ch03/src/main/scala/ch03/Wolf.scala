package ch03


class WolfGoatCabbage extends MissionariesAndCannibals {

  val F:Char = 'F'
  val W:Char = 'W'
  val G:Char = 'G'

  override def safe(st: State) = {
    !st.exists(x => !x.contains(F) &&
      (x.contains(W) && x.contains(G) || x.contains(G) && x.contains(C) ))
  }


  override def start() {
    opAll = List(List(F,W), List(F,G), List(F,C), List(F)).map(_.sorted)

    val st = List(List(F,W,G,C).sorted,List())
    val history = List(List()::List(L)::st)

    val solution = solve(st, opAll, -1, history)
    println("")
    solution.reverse.foreach(x =>
      println(x.map(_.mkString).mkString("\t\t")))
  }

}

object Wolf extends App {
  new WolfGoatCabbage().start
}
