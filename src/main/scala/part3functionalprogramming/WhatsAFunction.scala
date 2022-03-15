package part3functionalprogramming

object WhatsAFunction extends App {

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(7))

  val stringToIntConverter = new (String => Int) {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("3") + 4)

  val adder: (Int, Int) => Int = (v1: Int, v2: Int) => v1 + v2

  val concatenate: (String, String) => String = (str1: String, str2: String) => str1 + str2

  println(concatenate("abc", "def"))


  val specialFunction: Int => Int => Int = (x: Int) => (y: Int) => x + y
//  val specialFunction2: Int => Int => Int = (x: Int) => (y: Int) => x + y

  println(specialFunction(3)(4))
}

trait MyFunction[A, B] {
  def apply(element: A): B
}

trait MyPredicate[-T] {
  def test(elem: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(elem: A): B
}
