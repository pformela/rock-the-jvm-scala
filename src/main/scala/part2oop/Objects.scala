package part2oop

object Objects extends App {

  // equivalent to static in java
  object Person { // type and its only instance
    val N_EYES = 2
    def canFly: Boolean = false

    // factory method
    def apply(mother: Person, father: Person): Person = new Person("Bobby")
  }

  class Person(val name: String) {
    // class level functionality
  }

  println(Person.N_EYES)
  println(Person.canFly)

  val mary = new Person("Mary")
  val john = new Person("John")

  val bobby = Person(mary, john)

  // Scala object = SINGLETON instance


//  def main(args: Array[String]): Unit = {
//
//  }

}
