package part2oop

object CaseClasses {

  case class Person(name: String, age: Int)

  def main(args: Array[String]): Unit = {
    val jim = new Person("Jim", 34)
    println(jim.name)

    println(jim)
  }
}
