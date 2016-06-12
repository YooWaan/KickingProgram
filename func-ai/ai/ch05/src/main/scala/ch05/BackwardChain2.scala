package ch05

import scala.collection.immutable.HashMap

object BackwardChain2 extends InferenceEngineBackward with App {

  rules = ruleReader("""
   $x は $y である
   $y は $z である
   ->
   $x は $z である
,
   $x は 羽 を持つ
   ->
   $x は 鳥 である
""")

facts = factReader("""
   ピヨ は 羽 を持つ
   鳥 は 動物 である
""")

  backward(List(List("ピヨ", "は", "$何", "である")))
}
