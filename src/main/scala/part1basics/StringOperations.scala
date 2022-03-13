package part1basics

object StringOperations extends App {
  val str: String = "Hello, I am learning Scala"

  println(str.charAt(3))
  println(str.substring(7, 11))
  println(str.split(" ").toList)
  println(str.startsWith("Hello"))
  println(str.replace(" ", "-"))
  println(str.toLowerCase())
  println(str.toUpperCase())
  println(str.length)

  val aNumberString = "45"
  val aNumber = aNumberString.toInt
  println("a" +: aNumberString :+ "z")
  println(str.reverse)
  println(str.take(2))

  // Scala specific
  // String interpolators

  // S-interpolators
  val name: String = "John"
  val age: Int = 22
  val greeting: String = s"Hello, my name is $name and I am $age years old"

  val anotherGreeting: String = s"Hello, my name is $name and I will be turning ${age + 1} years old"

  // F-interpolators
  val speed: Float = 1.2f
  val myth: String = f"$name%s can eat $speed%2.2f burgers per minute"

  // raw-interpolator
  println(raw"This is a \n newline")
  val escaped: String = "This is a \n newline"
  println(raw"$escaped")

}
