package part2oop

object MethodNotations extends App {

  class Person(val name: String, favouriteMovie: String, age: Int = 0) {
    def likes(movie: String): Boolean = movie == favouriteMovie
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    def +(nickname: String): Person = new Person(s"${this.name} + ($nickname)", this.favouriteMovie)
    def unary_! : String = s"$name, what the heck?!"
    def isAlive: Boolean = true
    def apply(): String = s"Hi, my name is $name and I like $favouriteMovie"
    def apply(howManyTimes: Int): String = s"${this.name.toUpperCase()} watched inception $howManyTimes"
    def unary_+ : Person = new Person(this.name, this.favouriteMovie, this.age + 1)
    def getAge(): Int = this.age
    def learns(skill: String = "Scala"): String = s"$name learns $skill"
    def learnsScala(): String = this.learns("Scala")
  }

  val mary = new Person("Mary", "Inception", 20)
  println(mary.likes("Inception"))
  println(mary likes "Inception")  // infix notation / operator notation - only works with single method parameters - syntactic sugar

  val tom = new Person("Tom", "Fight Club")
  //  println(mary hangOutWith tom)
  println(mary + tom)
  println(mary.+(tom))

  // all operators are methods

  // prefix notation
  val x = -1
  val y = 1.unary_~

  println(!mary) // the same as below
  println(mary.unary_!)

  println(mary.isAlive)
//  println(mary isAlive)

  // apply
  println(mary.apply()) // equivalent to below
  println(mary())

  mary + "the rockstar"
  println(mary.name)
  println(mary.getAge())
  +mary
  println((+mary).getAge())

  println(mary.learns())
  println(mary.learnsScala())

  println(mary.apply(2))

}
