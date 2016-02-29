
// functions

def if2[A] (cond: Boolean, onTrue: => A, onFalse: => A): A =
  if (cond) onTrue else onFalse

def maybeTwice(b: Boolean, i: => Int ) = if (b) i+i else 0

def maybeTwice2(b: Boolean, i: => Int ) = {
  lazy val j = i
  if (b) j+j else 0
}


// samples


def call51_1 () = if2(false, sys.error("fail"), 3)

def call51_2 (): Int = maybeTwice(true, {println("hi") ; 1+41})

def call51_3 (): Int = maybeTwice2(true, {println("hi") ; 1+41})
