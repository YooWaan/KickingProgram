package ch04

import javafx.application._

object TTTMinMax {
  def main(args: Array[String]) {
    Application.launch(classOf[TicTacToeMinMaxApp], args: _*)
  }
}

class TicTacToeMinMaxApp extends TicTacToeGraphicsApp {
  override val game = new TicTacToeMinMax(this)
}

class TicTacToeMinMax(app: TicTacToeGraphicsApp) extends TicTacToeGraphics(app) {

  def f(p:Char, t: List[(Int, Int)]) = {
    val self = t.count(a => bd(a._1)(a._2) == p)
    val free = t.count(a => bd(a._1)(a._2) == SP)
    val other = 3 - self - free
    if (self > 0 && other == 0) scala.math.pow(3,self).toInt
    else if (other > 0 && self == 0) -scala.math.pow(3,other).toInt
    else 0
  }

  def eval(p: Char) = {
    pat.map(t => f(p,t)).sum
  }

  def search(p:Char, psw:Char, level:Int): ((Int,Int,Int), Int) = {
    val myTurn = psw == p
    var minmax = (0,0, if (myTurn) Int.MinValue else Int.MaxValue)
    var count = 0
    for (r <- 0 to 2; c <- 0 to 2 if bd(r)(c) == SP) {
      bd(r)(c) = psw
      val v = if (level == 1 || goal(psw) || fin) {
                 count +=1; eval(p)
              } else {
                 val v1 = search(p, turn(psw), level-1)
                 count += v1._2
                 v1._1._3
              }
      bd(r)(c) = SP
      if (myTurn && v > minmax._3 || !myTurn && v < minmax._3) {
        minmax = (r, c, v)
      }
    }
    (minmax, count)
  }

  override def computer(p: Char) {
    val ((r,c,v), cnt) = search(p,p,3)
    bd(r)(c) = p
    println("computer:" + p + " = " + (r,c) + " search = " + cnt)
  }

}