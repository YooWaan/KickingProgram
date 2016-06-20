package ch06

import javafx.application.Application


object BoidAppRule {
  def main(args: Array[String]) {
    Application.launch(classOf[BoidRule], args: _*)
  }
}

class BoidRule extends BoidApp {
  val chohesionRate = 0.01
  val separationDis = 25
  val alignmentRate = 0.5
  val speedLimit = 8

  override def init() {
    boids = Array.fill(count)(new BoidR(this))
  }
}


class BoidR(app: BoidRule) extends Boid(app) {

  override def moveDecide() {

  }

  def chohesion() {
    val cx = app.boids.map(_.x).sum / app.count
    val cy = app.boids.map(_.y).sum / app.count
    dx += (cx - x) * app.chohesionRate
    dy += (cy - y) * app.chohesionRate
  }

  def separation() {
    for (o <- app.boids if o != this) {
      val ax = o.x - x
      val ay = o.y - y
      val dis = math.sqrt(ax*ax + ay*ay)
      if (dis < app.separationDis) {
        dx -= ax
        dy -= ay
      }
    }
  }

  def alignment() {
    val ax = app.boids.map(_.dx).sum / app.count
    val ay = app.boids.map(_.dy).sum / app.count
    dx += (ax - dx) * app.alignmentRate
    dy += (ay - dy) * app.alignmentRate
  }

}
