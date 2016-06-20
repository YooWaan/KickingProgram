package ch06

import javafx.application.Application


object BoidAppRand {
  def main(args: Array[String]) {
    Application.launch(classOf[BoidRand], args: _*)
  }
}

class BoidRand extends BoidApp {
  override def init() {
    boids = Array.fill(count)(new BoidRnd(this))
  }
}

class BoidRnd(app: BoidApp) extends Boid(app) {
  override def moveDecide() {
    if (math.random < 0.05) {
      dx = (math.random * 2.0 - 1.0).round.toInt
    }
    if (math.random < 0.05) {
      dy = (math.random * 2.0 - 1.0).round.toInt
    }
  }
}
