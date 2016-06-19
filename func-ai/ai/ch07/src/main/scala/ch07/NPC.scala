package ch07

import javafx.scene.shape._
import javafx.scene.paint.Color


class NPC(app: GameApp) extends GameElem(app) {
  var nextDir = 0
  var dirOffset = Array((1,0), (0,1), (-1,0), (0,1))
  var tryPlan = Array(1,3,2)
  val s = new Rectangle(app.uw, app.uh)

  s.setArcWidth(15)
  s.setArcHeight(15)
  s.setScaleX(0.9)
  s.setScaleY(0.9)

  s.setStroke(Color.FIREBRICK)
  s.setFill(Color.INDIANRED)

  setShape(s)


  def nextMove() {
    nextDir = if (math.random < 0.005)
                (dir + 1 + (math.random * 2).round.toInt) % 4 else dir
  }

  def moveExec() {
    var x1 = x + dirOffset(nextDir)._1
    var y1 = y + dirOffset(nextDir)._2

    if (app.isWall(x1,y1) || app.getCollision(this,x1,y1) != -1) {
      nextDir = (nextDir + tryPlan((math.random * 2.1).toInt)) % 4
      x1 = x + dirOffset(nextDir)._1
      y1 = y + dirOffset(nextDir)._2
    }

    if (!app.isWall(x1,y1) && app.getCollision(this,x1,y1) == -1) {
      dir = nextDir
      setPos(x1, y1)
    }
  }

  override def move() {
    nextMove
    moveExec
  }

}
