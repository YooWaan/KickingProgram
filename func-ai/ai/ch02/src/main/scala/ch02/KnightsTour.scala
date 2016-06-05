package ch02

class KnightsTour(val n: Int) {

  val bd = Array.fill(n,n)(0)
  val pat = for (a <- List((1,2),(2,1));
    b <- List(1,-1); c <- List(1,-1)) yield (a._1*b, a._2*c)

  def knight(r: Int, c: Int, cnt: Int = 1, route: List[(Int,Int)] = Nil): List[(Int,Int)] = {
    if (r >= 0 && r < n && c >= 0 && c < n && bd(r)(c) == 0) {
      bd(r)(c) = cnt
      if (cnt == n*n) return (r,c)::route
      for (p <- pat) {
        val rt = knight(r+p._1, c+p._2, cnt+1, (r,c)::route)
        if (rt != Nil) return rt
      }
      bd(r)(c) = 0
    }
    Nil
  }

  def start(r: Int, c: Int) {
    println(knight(r,c))
    println(bd.map(_.map("%02d ".format(_)).mkString).mkString("\n"))
  }

}

object KnightsTourApp extends App {
  new KnightsTour(5).start(0, 0)
}
