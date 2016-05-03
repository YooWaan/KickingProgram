package ch04


class TicTacToe {

  val SP: Char = ' '
  val OK: Char = 'o'
  val NG: Char = 'x'

  val bd = Array.fill(3)(Array.fill(3)(SP))
  val pat = { val a = List(0,1,2);
    a.map(x => (x,x)) :: a.map(x => (2-x,x)) ::
    a.map(r => a.map(c => (r,c))) :::
    a.map(c => a.map(r => (r,c)))
  }
  var playing = true
  var winner = ' '

  def goal(p: Char) = {
    pat.exists(t => t.forall(a => bd(a._1)(a._2) == p))
  }

  def fin(): Boolean = {
    for (r <- 0 to 2; c <- 0 to 2 if bd(r)(c) == SP) return false
    true
  }

  def computer(p: Char) {
    val free = for (r <- 0 to 2; c<-0 to 2 if bd(r)(c) == SP)
    yield (r,c)

    val n = free.length
    val (r,c) = free(scala.util.Random.nextInt(n))
    bd(r)(c) = p
    println("computer:" + p + " = " + (r,c))
  }

  def human(p: Char) {
    print("row col =>")
    val s = new java.util.Scanner(System.in)
    val (r,c) = (s.nextInt, s.nextInt)
    bd(r)(c) match {
      case SP => bd(r)(c) = p
      case _ => human(p)
    }
  }

  def turn(p: Char) = { if (p == OK) NG else OK }

  def disp() {
    println(bd.map(_.mkString("|")).mkString("\n"))
  }

  def play() {
    var p = OK;
    disp
    do {
      if (p == OK) human(p)
      else         computer(p)
      disp
      if (goal(p)) {
        winner = p
        playing = false
      } else if (fin) {
        playing = false
      } else {
        p = turn(p)
      }
    } while(playing)

    if (winner != SP) println(winner + " Win!")
    else              println("drawn")
  }
}

object TicTacToeApp extends App {
  new TicTacToe().play
}
