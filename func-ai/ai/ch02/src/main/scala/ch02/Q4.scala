package ch02


class Q4(n: Int) extends Queen(n) {
  type Ret = List[List[(Int, Int)]]
  var level = 0
  var indicator = "- "

  def trace(fname: String, arg: Any*)(body: => Ret): Ret = {
    val args = arg.mkString(",")
    println((indicator * level) + level + ":" + fname + " (" + args + ")")

    level += 1
    var ret = body
    level -= 1
    println((indicator*level) + level + ":" + fname + " = List(")
    ret.foreach(x => println(indicator*(level+1) + x))
    println(indicator*(level) + ")")
    ret
  }

  override def queen(r: Int): List[List[(Int, Int)]] = {
    trace("queen", r) {
      if (r == 0) List(Nil)
      else for (p <- queen(r - 1); c <- 1 to n if check(r, c, p))
            yield (r, c)::p
    }
  }

}

object Q4App extends App {
  new Q4(4).start
}
