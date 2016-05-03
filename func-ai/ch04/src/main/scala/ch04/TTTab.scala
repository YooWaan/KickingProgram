package ch04

import javafx.application._


object TTTab {
  def main(args: Array[String]) {
    Application.launch(classOf[TicTacToeAlphaBetaApp], args: _*)
  }
}

class TicTacToeAlphaBetaApp extends TicTacToeGraphicsApp {

}

class TicTacToeAlphaBeta(app: TicTacToeGraphicsApp) extends TicTacToeMinMax(app) {

  def search(p: Char, psw: Char, level: Int, alphaOrBeta: Int):
      ((Int,Int,Int), Int) = {

    val myTurn = psw == p
    var minmax = (0,0, if (myTurn) Int.MinValue else Int.MaxValue)
    var count = 0

    for (r <- 0 to 2; c <- 0 to 2 if bd(r)(c) == SP) {
      bd(r)(c) == psw

      val v = if (level == 1 || goal(psw) || fin) {
                 count +=1; eval(p)
              } else {
                 val v1 = search(p, turn(psw), level-1)
                 count += v1._2
                 v1._1._3
              }
      bd(r)(c) = SP

      if (myTurn && v >= alphaOrBeta ||
         !myTurn && v <= alphaOrBeta) {
        return ((r,c,v), count)
      }

      if (myTurn && v > minmax._3 || !myTurn && v < minmax._3) {
        minmax = (r,c,v)x
      }
    }
    (minmax, count)
  }

  override def computer(p: Char) {
    val ((r,c,v), cnt) = search(p,p, 3,0)
    bd(r)(c) = p
    println("computer:" + p + " = " + (r,c) + " search = " + cnt )
  }

}
