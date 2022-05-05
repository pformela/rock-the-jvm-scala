package part3functionalprogramming

object AnonymousFunctions extends App {

  val doubler = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 * 2
  }

  val anonymousDoubler: Int => Int = (x: Int) => x * 2

  val adder: (Int, Int) => Int = (x: Int, y: Int) => x + y

  val justDoSomething: () => Int = () => 3

  println(justDoSomething)  // function itself
  println(justDoSomething())  // lambda call

  // curly braces with lambdas
  val strintToInt = { (str: String) => str.toInt }

  val niceIncrementer: Int => Int = (x: Int) => x + 1
  val nicerIncrementer: Int => Int = _ + 1

  val niceAdder: (Int, Int) => Int = _ + _  // equivalent to (a, b) => a + b

  val specialAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y
  

}
