package ch07

import javafx.scene.shape._
import javafx.scene.input._
import javafx.scene.paint.Color
import scala.collection.mutable.ArrayBuffer

class GameElem(app: GameApp) {
  var typ, dir, x, y, r, c = 0
  var shape: Shape = null
  var reached = false

  def setShape(shp: Shape) {
    shape = shp
    app.shapes.add(shape)
  }

  def move() {}

  def draw() {
    shape.setLayoutX(x)
    shape.setLayoutY(y)
  }

  def setPos(x1: Int, y1: Int) {
    x = x1
    y = y1
    r = y / app.uh
    c = x / app.uw
    reached = (y % app.uh == 0 && x % app.uw == 0)
  }
}


object GameElem {
  val ROAD = 0
  val WALL = 1
  val PLAYER = 2
  val ALIEN = 3

  def makeElems(m: Array[Array[Int]], app: GameApp,
    makeElem: (Int) => GameElem) = {

    val elems = new ArrayBuffer[GameElem]
    for (r <- 0 until m.length; c <- 0 until m(r).length if m(r)(c) != ROAD) {
      val e = makeElem(m(r)(c))
      e.typ = m(r)(c)
      e.setPos(c * app.uw, r * app.uh)
      if (e.typ == WALL) {
        e.draw
      } else {
        elems.append(e)
      }
    }
    elems
  }

  def makeElem(typ: Int, app: GameApp) = {
    typ match {
      case WALL => new Wall(app)
      //case PLAYER => new Player(app)
      //case ALIEN => new Alien(app)
      case _ => null
    }
  }

}

class Wall(app: GameApp) extends GameElem(app) {
  val s = new Rectangle(app.uw, app.uh)
  s.setFill(Color.DARKGRAY)
  setShape(s)
}
