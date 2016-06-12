package ch05

import scala.collection.immutable.HashMap

object BackwardChain1 extends InferenceEngineBackward with App {

  rules = ruleReader("""
   $x parent $y
   $ is-a $z
   ->
   $x is-a $z
,
   $x has claws
   $x has forward_eyes
   ->
   $x is-a carnivore
,
   $x is-a carnivore
   $x has black_stripes
   $x says gaoooo
   ->
   $x is-a tiger
,
   $x is-a carnivore
   $x has black_stripes
   $x says nyaa
   ->
   $x is-a tabby
""")

   facts = factReader("""
   momo has claws
   momo has forward_eyes
   momo has black_stripes
   momo say nyaa
   mimi parent momo
""")

  backward(List(List("$who", "is-a", "$animal")))
}
