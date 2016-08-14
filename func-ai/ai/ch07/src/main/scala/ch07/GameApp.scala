package ch07


import scala.collection.mutable.ArrayBuffer

import javafx.application.Application
import javafx.scene._
import javafx.stage._
import javafx.scene.layout._
import javafx.event.EventHandler
import javafx.scene.input._
import javafx.animation._


object GameAppMain {
  def main(args: Array[String]) {
    Application.launch(classOf[GameApp], args: _*)
  }
}


class GameApp extends Application {
  val uw, uh = 40
  var w, h: Int = 0
  val pane = new Pane
  val shapes = pane.getChildren
  var map: Array[Array[Int]] = null
  var elems: ArrayBuffer[GameElem] = null
  var active = true
  var keyCode: KeyCode = null

  override def init() {
    map = GameMap.makeMap
    elems = GameElem.makeElems(map, this, makeElem)
    h = map.length * uh
    w = map(0).length * uw
  }

  def makeElem(typ: Int) = {
    typ match {
      case GameElem.WALL => new Wall(this)
      case GameElem.PLAYER => new Player(this)
      case GameElem.NPC => new NPC(this)
      case _ => null
    }
  }

  override def start(stage: Stage) {
    val scene = new Scene(pane, w, h)
    stage.setScene(scene)
    stage.show

    scene.setOnKeyPressed(new EventHandler[KeyEvent] {
      def handle(e: KeyEvent) {keyCode = e.getCode}
    })

    scene.setOnKeyReleased(new EventHandler[KeyEvent] {
      def handle(e: KeyEvent) {keyCode = KeyCode.ESCAPE}
    })

    new Thread() {
      override def run() {
        while (active) {
          for (e <- elems if e.typ >= GameElem.PLAYER) e.move
          Thread.sleep(8)
        }
      }
    }.start

    new AnimationTimer {
      override def handle(now: Long) {
        for (e <- elems if !(e.typ == GameElem.ROAD ||
                             e.typ == GameElem.WALL)) e.draw
      }
    }.start
  }


  override def stop() {active = false}

  def isWall(x: Int, y: Int) = {
    val r1 = y / uh
    val c1 = x / uw
    val r2 = (y + uh - 1) / uh
    val c2 = (x + uw - 1) / uw
    map(r1)(c1) == GameElem.WALL || map(r1)(c2) == GameElem.WALL ||
    map(r2)(c1) == GameElem.WALL || map(r2)(c2) == GameElem.WALL
  }

  def getCollision(me: GameElem, x: Int, y: Int): Int = {
    for (other <- elems if other.typ > 1 && other != me) {
      if (math.abs(other.x - x) < uw && math.abs(other.y - y) < uh)
        return other.typ
    }
    -1
  }

}

