package ch01

import javafx.application.Application
import javafx.scene.canvas._


object KochMain {
  def main(args: Array[String]) {
    Application.launch(classOf[KochApp], args: _*)
  }
}

class KochApp extends CurveApp {
  override def draw() {
    val c = new Koch(g)
    c.move(50,300);
    c.draw(5,500,0);
  }
}

class Koch(g: GraphicsContext) extends Curve(g) {

  def draw(n: Int, len: Double, angle: Double) {
    if (n == 1) {
      forward(len, angle)
    } else {
      val l = len / (2/math.sqrt(2.0) + 2)
      val a = math.Pi * 0.25
      draw(n-1,l,angle)
      draw(n-1,l,angle-a)
      draw(n-1,l,angle+a)
      draw(n-1,l,angle)
    }
  }

}
