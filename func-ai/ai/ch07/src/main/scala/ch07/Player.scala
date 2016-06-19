package ch07

import javafx.scene.shape._
import javafx.scene.input._
import javafx.scene.paint.Color


class Player(app: GameApp) extends GameElem(app) {
  val s = new Rectangle(app.uw, app.uh)
  s.setArcWidth(15)
  s.setArcHeight(15)
  s.setScaleX(0.9)
  s.setScaleY(0.9)
  s.setStroke(Color.ROYALBLUE)
  s.setFill(Color.CORNFLOWERBLUE)
  setShape(s)
  var lastDx, lastDy = -1


  override def move() {
    var dx, dy = 0
    app.keyCode match {
      case KeyCode.LEFT => dx = -1
      case KeyCode.RIGHT => dx = 1
      case KeyCode.UP => dy = -1
      case KeyCode.DOWN => dy = -1
      case KeyCode.ESCAPE =>
        if (x % app.uw != 0 || y % app.uh != 0) {
          dx = lastDx
          dy = lastDy
        } else {
          app.keyCode = null
          return
        }
      case _ => return
    }

    val x1 = x + dx
    val y1 = y + dy
    if (!app.isWall(x1,y1) && app.getCollision(this, x1, y1) == -1) {
      setPos(x1, y1)
      lastDx = dx
      lastDy = dy
    }
  }

}
