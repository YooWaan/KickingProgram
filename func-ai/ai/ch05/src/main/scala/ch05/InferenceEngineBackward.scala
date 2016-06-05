package ch05

import scala.collection.immutable.HashMap
import scala.collection.mutable.HashSet

class InferenceEngineBackward extends InferenceEngineForward {

  type Sol = HashMap[String,String]

  def patMatchDual(p1: Pat, p2: Pat, env: Env = new Env, sol: Sol = new Sol): (Env, Sol) = {
    (p1, p2) match {
      case (Nil, Nil) => (env, sol)
      case (_,Nil) | (Nil,_) => (null, null)
      case (a::aa, b::bb) if b(0) == '$' => 
        if (sol.contains(b)) {
          if (sol(b) == a) patMatchDual(aa, bb, env, sol) else null
        } else {
          patMatchDual(aa, bb, env, sol + (b -> a))
        }
      case (a::aa, b::bb) if a(0) == '$' =>
        if (env.contains(a)) {
          if (env(a) == b) patMatchDual(aa, bb, env, sol) else null
        } else {
          patMatchDual(aa, bb, env + (a -> b), sol)
        }
      case (a::aa, b::bb) if a == b => patMatchDual(aa, bb, env, sol)
      case _ => (null, null)
    }
  }

  def actionMatch(actions: List[Pat], pat: Pat, env: Env = new Env, sol: Sol = new Sol): (Env, Sol) = {
    actions match {
      case Nil => (null, null)
      case a::aa => Option(patMatchDual(a, pat, env, sol))
        .getOrElse(actionMatch(aa, pat, env, sol))
    }
  }

  def applyVal(sol: Sol, env: Sol): Sol = {
    sol.map(s => if (s._2(0) == '$')
      s._1 -> env.get(s._2).getOrElse(s._2) else s)
  }

  def deduceFact(pat: Pat, pSet: HashSet[Pat], env: Env): List[Sol] = {
    var sols: List[Sol] = Nil
    for (rule <- rules) {
      rule match {
        case conds::acts::Nil =>
        val (env1, var1) = actionMatch(acts, pat)
        if ((env1, var1) != (null, null)) {
          sols ++= backwardMatch(conds.map(
            applyEnv(_,env1)), pSet).
            map(applyVal(var1 ++ env, _)).map(applyVal(_, env1))
        }
        case _ =>
      }
    }
    sols
  }

  def backwardMatch1(pat:Pat, pSet:HashSet[Pat], env:Env): List[Sol] = {
    if (pSet.contains(pat)) return Nil
    val pat1 = applyEnv(pat, env)
    val sols1 = facts.map(patMatch(pat1, _, env)).filter(_ != null)
    val sols2 = deduceFact(pat1, pSet + pat1, env)
    sols1 ::: sols2
  }

  def backwardMatch(patterns: List[Pat], pSet: HashSet[Pat] = new HashSet(), env: Env = new Env): List[Sol] = {
    patterns match {
      case Nil => List(env)
      case pat::tail => backwardMatch1(pat, pSet, env).map(
        backwardMatch(tail, pSet, _)).flatten
    }
  }

  def backward(patterns: List[Pat]) {
    val solutions = backwardMatch(patterns)
    println("--- Answer --------")
    solutions.foreach(println(_))
    println("--- Aacts ---------")
    solutions.foreach(s =>
      patterns.foreach(
        p => println(applyEnv(p, s))
      )
    )
  }

}


