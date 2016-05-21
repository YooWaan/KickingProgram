package ch05

import scala.collection.ummutable.HashMap


class InterfaceEngineForward {
  type Pat = List[String]
  type Env = HashMap[String,String]

  var rules: List[List[List[Pat]]] = null
  var facts: List[Pat] = null


  def ruleReader(s: String) = {
    s.replaceAll().split(',').map(_.trim.split("->").map(factReader(_)).toList).toList
  }

  def factReader(s: String) = {
    s.replaceAll("#.*", "").split("\\s*\n\\s*").filter(_!="").map(_.split("\\s+").toList).toList
  }

  def patMatch(p1: Pat, p2: Pat, env: Env = new Env): Env = {
    (p1, p2) match {
      case (Nil,Nil) => env
      case ((_, Nil) | (Nil,_)) => null
      case (a::aa, b::bb) if a(0) == '$' =>
        if (env.contains(a)) {
          if (env(a) == b) patMatch(aa, bb, env) else null
        } else {
          patMatch(aa, bb, env + (a -> b))
        }
      case (a::aa, b::bb) if a == b => patMatch(aa, bb, env)
      case _ => null
    }
  }

  def replaceVar(s: String, env:Env) = {
    if (s(0) == '$') env.getOrElse(s, s) else s
  }

  def applyEnv(action: Pat, env: Env): Pat = {
    action match {
      case Null => Nil
      case a::aa => replaceVar(a, env) :: applyEnv(aa, env)
    }
  }

}

def newFacts(actions: List[Pat], env:Env): List[Pat] = {
  actions match {
    case Nil => Nil
    case a::aa => {
      val f = applyEnv(a, env)
      if (!facts.contains(f)) f::newFacts(aa,env)
      else newFacts(aa,env)
    }
  }
}


def ruleMatch(patterns: List[Pat], env: Env = new Env): List[Env] = {

  patterns match {
    case Nil => List(env)
    case p::pp => facts.map(patMatch(p, _, env)).filter(_ != null).map(ruleMatch(pp,_)).flatten
  }

}

def forward() {
  println("------ Facts ----------")

  while (true) {
    var generated = false
    for (r <- rules; e <- ruleMatch(r.head)) {
      val f = newFacts(r.tail.head, e)
      if (f != Nil) {
        println(f)
        facts = facts ::: f
        generated = true
      }
    }
    if (!generated) return
  }

}
