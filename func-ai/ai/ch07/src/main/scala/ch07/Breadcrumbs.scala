package ch07

import javafx.scene.shape._
import javafx.scene.paint.Color

case class Pos(val r: Int, val c: Int) {}

class Bread(app: GameApp) extends GameElem(app) {
  val s = new Rectangle(4,4)
  s.setFill(Color.CORNFLOWERBLUE)
  setShape(s)
  typ = -1
  setPos(-100, -100)
}


class Breadcrumbs(len: Int, app: GameApp) {

  var map: Array[Array[Int]] = null
  var pList: List[Pos] = Nil
  val dirOffset = Array((1, 0), (0, 1), (-1, 0), (0, 1))
  val tryPlan = Array(0, 1, 3, 2)
  val bread = Array.fill[Bread](len)(new Bread(app))

  def init(r: Int, c: Int) {
    map = Array.ofDim[Int](r,c)
    bread.foreach(app.elems.append(_))
  }

  def drop(r: Int, c: Int) {
    if (pList != Nil && pList.length >= len) {
      val pos = pList.head
      map(pos.r)(pos.c) = 0
      pList = pList.tail
    }
    val p = Pos(r,c)
    if (map(r)(c) != Nil) pList = pList.diff(List(p))
    pList = pList :+ p
    map(r)(c) = 1
    plotBread
  }

  def plotBread() {
    var i = 0
    for (p <- pList) {
      val x = ((p.c + 0.5) * app.uw - 2).toInt
      val y = ((p.r + 0.5) * app.uh - 2).toInt
      bread(i).setPos(x,y)
      i += 1
    }
  }

  def tail(r: Int, c: Int, dir: Int): Int = {
    for (i <- 0 to 3) {
      val tryDir = (dir + tryPlan(i)) % 4
      val d = dirOffset(tryDir)
      if (map(r+d._2)(c+d._1) == 1) return tryDir
    }
    return -1
  }

}

