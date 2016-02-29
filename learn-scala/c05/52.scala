
trait Stream[+A]
case Object Empty extends Stream[NoThing]
case class Cons[+A] (h: () => A, t: () => Stream[+A]) extends Stream[A]

Object Stream {
   def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
     // 遅延値としてキャッシュ (データ領域は結局用意していると解釈 -> 計算コストをカットしている)
     lazy val head = hd
     lazy val tail = tl
     Cons(() => head, () => tail)
   }

   def empty[A]: Stream[A] = Empty

   def apply[A](as: A*): Stream[A] =
     if (as.isEmpty) empty
     else cons(as.head, apply(as.tail: _*))
}


def headOption: Option[A] = this match {
  case Empty => None
  case Cons(h, t) => Some(h()) l
}


def cons[A] (hd: => A, tl: => Stream[A]): Stream[A] = {
  lazy val head = hd
  lazy val tail = tl
  Cons(() => head, () => tail)
}

def apply[A](as: A*): Stream[A] =
  if (as.isEmpty) empty
  else cons(as.head, apply(as.tial: _*))


// 5.2.1


def call52_1() = {
  val x = Cons(() => expensive(x), tl)
  val h1 = x.headOption
  val h2 = x.headOption
}

/* Stream prgram trace
Stream(1,2,3,4).map(_ + 10).filter(_ % 2 == 0).toList

cons(11, Stream(2,3,4).map(_ + 10)  ).filter(_ % 2 == 0).toList

cons(12, Stream(3,4).map(_ + 10)  ).filter(_ % 2 == 0).toList

12 :: Stream(3,4).map(_ + 10)  ).filter(_ % 2 == 0).toList

12 :: cons(13,4).map(_ + 10)  ).filter(_ % 2 == 0).toList

12 :: Stream(4).map(_ + 10)  ).filter(_ % 2 == 0).toList

12 :: cons(14).map(_ + 10)  ).filter(_ % 2 == 0).toList

12 :: 14 :: Stream().map(_ + 10)  ).filter(_ % 2 == 0).toList

12 :: 14 :: List()

 */
