package ch06

import javafx.application.Application
import javafx.scene._
import javafx.stage._
import javafx.scene.layout._
import javafx.scene.canvas._
import javafx.scene.shape._
import javafx.scene.text._
import javafx.scene.effect._
import javafx.scene.control._
import javafx.geometry._
import javafx.scene.paint.Color
import javafx.event.EventHandler
import javafx.animation._


object BoidAppMain {
  def main(args: Array[String]) {
    Application.launch(classOf[BoidApp], args: _*)
  }
}


class BoidApp extends Application {
  val w = 800
  val h = 800
  val count = 30
  val range = 100
  val pane = new Pane
  val shapes = pane.getChildren
  var boids: Array[Boid] = null
  var active = true

  override def init() {
    boids = Array.fill(count)(new Boid(this))
  }

  override def start(stage: Stage) {
    stage.setScene(new Scene(pane, w, h))
    stage.show

    new Thread() {
      override def run() {
        while (active) {
          for (b <- boids) {
            b.moveDecide
            b.move
          }
          Thread.sleep(15)
        }
      }
    }.start

    new AnimationTimer {
      override def handle(now: Long) {
        for (b <- boids) {
          b.draw
        }
      }
    }.start
  }

  override def stop() { active = false }
}


class Boid(app: BoidApp) {

  var x, y = app.w / 2 + app.range * (math.random - 0.5)
  var dx, dy = 1.0
  val shape = new Rectangle(10, 10)
  app.shapes.add(shape)

  def moveDecide() {
    dx = (math.random * 2.0 - 1.0).round.toInt
    dy = (math.random * 2.0 - 1.0).round.toInt
  }

  def move() {
    x += dx
    y += dy
    if (x < 0 || x >= app.w) { dx = -dx; x += dx * 2}
    if (y < 0 || y >= app.h) { dy = -dy; y += dx * 2}
  }

  def draw() {
    shape.setX(x-5)
    shape.setY(y-5)
  }

}
