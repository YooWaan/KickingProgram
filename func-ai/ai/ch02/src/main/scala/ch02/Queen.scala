package ch02


class Queen(val n: Int) {

  def check(r: Int, c: Int, pat: List[(Int,Int)]): Boolean  = {
    pat.forall(p => c != p._2 && r-p._1 != math.abs(c-p._2))
  }

  def queen(r: Int): List[List[(Int, Int)]] = {
    if (r == 0) List(Nil)
    else for (p <- queen(r - 1); c <- 1 to n if check(r, c, p))
          yield (r, c)::p
  }

  def start() {
    queen(n).foreach(pat => println(pat.map(p => "+" * (p._2-1) + "Q" + "+" * (n-p._2)+ "\n").mkString))
  }

}

object QueeApp extends App {
  new Queen(8).start
}
