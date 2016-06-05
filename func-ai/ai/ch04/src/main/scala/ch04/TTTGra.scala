package ch04

import javafx.application._
import javafx.scene.Scene
import javafx.stage._
import javafx.scene.layout.StackPane
import javafx.scene.canvas._
import javafx.scene.shape._
import javafx.scene.text._
import javafx.scene.effect._
import javafx.scene.control._
import javafx.scene.input.MouseEvent
import javafx.event.EventHandler
import javafx.geometry._



object TTTGra {
  def main(args: Array[String]) {
    Application.launch(classOf[TicTacToeGraphicsApp], args:_*)
  }
}

class TicTacToeGraphicsApp extends Application {
  val w, h = 380
  val canvas = new Canvas(w, h)
  val g = canvas.getGraphicsContext2D
  val game = new TicTacToeGraphics(this)

  override def start(stage: Stage) {
    val pane = new StackPane
    pane.getChildren.add(canvas)
    stage.setScene(new Scene(pane))
    stage.show

    new Thread() {
      override def run() { game.play }
    }.start

    pane.setOnMouseClicked( new EventHandler[MouseEvent] {
      def handle(e: MouseEvent) {
        if (game.selR == -1) {
          game.selC = (e.getX / w * 3).toInt
          game.selR = (e.getY / h * 3).toInt
        }
      }
    })
  }

  override def stop() { game.playing = false }

  def draw(bd: Array[Array[Char]]) {
    val (dw, dh) = (w/3, h/3)
    g.clearRect(0,0,w,h)
    for (i <- 1 to 2) {
      val (x,y) = (i * dw, i * dh)
      g.strokeLine(x,0,x,h);g.strokeLine(0,y,w,y)
    }
    val (mw, mh) = (dw * 0.6, dh * 0.6)
    for (r <- 0 to 2; c <- 0 to 2 if bd(r)(c) != game.SP) {
      val (x1, y1) = (c*dw + (dw-mw)*0.5, r*dh + (dh-mh)*0.5)
      val (x2, y2) = (x1 + mw, y1 + mh)

      /*
      println("mw,mh = " + (mw, mh)
        + "| x1,y1 = " + (x1, y1)
        + "| x2,y2 = " + (x2,y2))
       */

      bd(r)(c) match {
        case game.OK => g.strokeOval(x1,y1, mw, mh)
        case game.NG => g.strokeLine(x1,y1, x2, y2)
                        g.strokeLine(x2,y1, x1, y2)
      }
    }
  }

}



class TicTacToeGraphics(app: TicTacToeGraphicsApp) extends TicTacToe {

  var selR, selC = -1

  override def human(p: Char) {
    selR = -1; selC = -1
    while (selR == -1 && selC == -1) {
      Thread.sleep(100)
      if (!playing) return
    }
    if (bd(selR)(selC) != SP) human(p)
    else bd(selR)(selC) = p
    print("human :" + p + " = " + (selR, selC))
  }

  override def disp() {
    var bdCopy = bd.map(_.clone)
    Platform.runLater(new Runnable{
      def run() { app.draw(bdCopy)}
    })
  }
}
