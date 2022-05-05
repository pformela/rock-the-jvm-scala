package part3functionalprogramming

object WhatsAFunction extends App {

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  val stringToIntConverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("3") + 4)

  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  // Function2[A, B, R] === (A, B) => R

  val concatenate: (String, String) => String = (a, b) => a + b

//  val weirdFunc: Int => Int => Int = a => a + 2 => a * 2
  val specialFunction: Int => Int => Int = (x: Int) => (y: Int) => x + y

  val specialFunction2: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(x: Int): Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val adder3 = specialFunction(3)
  println(adder3(4))
  println(specialFunction(4)(3)) // curried function - function that returns a function
}

trait MyFunction[A, B] {
  def apply(element: A): B
}
