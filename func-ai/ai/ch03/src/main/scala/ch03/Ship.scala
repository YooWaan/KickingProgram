package ch03

class MissionariesAndCannibals {
  type State = List[List[Char]]
  type Op    = List[Char]
  var opAll:List[Op] = Nil

  val M:Char = 'M'
  val C:Char = 'C'
  val L:Char = '<'
  val R:Char = '>'

  def move(from:List[Char], to:List[Char], op: Op): State = {
    val from1 = from.diff(op)
    if (from1.length == from.length - op.length)
      List(from1, op:::to)
    else  List(from, to)
  }

  def solve(st: State, ops: List[Op], boat: Int, history: List[State]): List[State] = {
    ops match {
      case Nil => Nil
      case op::opTail => {
        val (dir, stNew) = st match {
          case List(l,r) =>
            if (boat == -1) (List(R), move(l,r,op).map(_.sorted))
            else (List(L), move(l,r,op).reverse.map(_.sorted))
        }
        if (goal(stNew)) (op::dir::stNew)::history
        else if (stNew == st || !safe(stNew) ||
             history.exists(_.tail == dir::stNew))
          solve(st, opTail, boat, history)
        else {
          val ret = solve(stNew, opAll, -boat, (op::dir::stNew)::history)
          if (ret != Nil) ret
          else solve(st, opTail, boat, history)
        }
      }
    }
  }

  def goal(st: State) = {
    st.head == Nil
  }

  def safe(st: State) = {
    st.forall(x => x.count(_ == M) == 0 || x.count(_ == M) >= x.count(_ == C))
  }

  def start() {
    opAll = List(List(M,M), List(M,C), List(C,C), List(M), List(C)).map(_.sorted)
    val st = List(List(M,M,M,C,C,C), List()).map(_.sorted)
    val history = List(Nil::List(L)::st)
    val solution = solve(st, opAll, -1, history)
    println("")
    solution.reverse.foreach(x =>
      println(x.map(_.mkString).mkString("\t\t")))
  }

}


object Ship extends App {
  new MissionariesAndCannibals().start
}
