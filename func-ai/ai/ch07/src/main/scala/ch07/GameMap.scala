package ch07

object GameMap {
  var r, c = 0
  val mark = "＿＃◯＠"
  val mapData = 
"""
＃＃＃＃＃＃＃＃＃＃＃＃
＃＠＿＿＿＿＃＃＿＿＠＃
＃＿＃＿＃＿＿＿＿＃＿＃
＃＿＃＿＃＃＿＃＃＃＿＃
＃＿＿＿＿＃＿＃＿＿＿＃
＃＃＿＿＿＿＿＃＿＃＃＃
＃＿＿＿＃＿＿＿＿＿＿＃
＃＿＃＿＃＃＃＿＃＃＿＃
＃＿＃＿＿＿＿＿＿＃＿＃
＃＿＃＃＿＃＿＃＿＃＿＃
＃＠＿＿＿＃＿＿＿＿◯＃
＃＃＃＃＃＃＃＃＃＃＃＃
"""

  def makeMap() = {
    val a = mapData.split("\\n").map(_.trim).filter(_ > "")
    .map(_.split("").map(mark.indexOf(_)))
    r = a.length
    c = a(0).length
    a
  }

}
