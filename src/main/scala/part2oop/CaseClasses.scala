package part2oop

object CaseClasses {

  case class Person(name: String, age: Int)

  def main(args: Array[String]): Unit = {
    val jim = new Person("Jim", 34)
    println(jim.name)

    println(jim)

    val jim2 = new Person("Jim", 34)

    println(jim == jim2)

    val jim3 = jim.copy(age = 45)  // copying with changed attribute
    println(jim3)

    val thePerson = Person  // valid definition only for case classes
    val mary = Person("Mary", 23)

    // CCs implement equals and hashCode
    // CCs are serializable
    // CCs can be used in pattern matching

    case object UnitedKingdom {
      def name: String = "The UK of GB and NI"
    }


  }
}
