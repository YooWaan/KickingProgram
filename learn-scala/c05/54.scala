


def call54_1() = {
  val ones: Stream[Int] = Stream.cons(1, ones)
  ones.take(5).toList
  ones.exists(_ % 2 != 0)
}
