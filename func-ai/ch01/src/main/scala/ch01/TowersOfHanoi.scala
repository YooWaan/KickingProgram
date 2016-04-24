package ch01


import scala.Range


// n  number of circle
class TowersOfHanoi(val n: Int) {

  def hanoi(m: Int, from: Int, to: Int, work: Int, s: Array[List[Int]]) {
    if (m == 1) {
      s(to) = s(from).head::s(to)
      s(from) = s(from).tail
      println(disp(s.toList.map(_.reverse)))
    } else {
      hanoi(m-1, from, work, to,   s)  // from -> work
      hanoi(1,   from, to,   work, s)  // from -> to
      hanoi(m-1, work, to,   from, s)  // work -> to
    }
  }

  def disp(a: List[List[Int]]) : String = {
    if (a == List(Nil, Nil, Nil)) {
      "ー" * (n * 2 * 3) + "\n"
    } else {
      disp(
        a.map(x => if (x == Nil) Nil else x.tail)) + "\n" +
      a.map(x => if (x == Nil) 0 else x.head)
        .map(x => " "*(n-x) + "■■" * x + "　" * (n-x) + "　").mkString
    }
  }

  def start() {
    val s = Array(Range(1, n+1).toList, Nil, Nil)
    hanoi(n, 0, 2, 1, s)
  }

}

/*
object TowersOfHanoiApp extends App {
  new TowersOfHanoi(4).start
}
*/
