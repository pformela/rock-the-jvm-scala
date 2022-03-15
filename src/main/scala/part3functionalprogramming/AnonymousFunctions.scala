package part3functionalprogramming

object AnonymousFunctions extends App {

  val doubler: Int => Int = (x: Int) => x * 2
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  val justDoSomething: () => Int = () => 3

  val stringToInt = { (str: String) =>
    str.toInt
  }

  val niceIncrementer: Int => Int = _ + 1  // equivalent to x => x + 1

  val niceAdder: (Int, Int) => Int = _ + _  // (a , b) => a + b
  val specialAdder: (Int, Int) => Int = _ + _

  val superAdd = (x: Int) => (y: Int) => x + y

}
