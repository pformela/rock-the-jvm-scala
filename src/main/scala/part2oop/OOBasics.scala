package part2oop

object OOBasics extends App {
  val person = new Person("John", 26)

  println(person.age)


}

class Person(name: String, val age: Int = 0) { // constructor of a class
  // class parameters are not fields, unless there is a val before a parameter name
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")

  def greet(): Unit = println(s"Hi, I am $name")

  // multiple constructors
  def this(name: String) = this(name, 0) // default value for age

}

class Writer(firstName: String, surname: String, year: Int) {
  def fullname(): String = firstName + " " + surname

  def getYear(): Int = year
}

class Novel(name: String, yearOfRelease: Int, author: Writer) {
  def getAuthorAge(): Int = yearOfRelease - author.getYear()

  def isWrittenBy(author: String): Boolean = author == this.author.fullname()

  def copy(newYear: Int): Novel = new Novel(this.name, newYear, this.author)
}

class Counter(value: Int = 0) {
  def currentCount(): Int = value

  def increment(): Counter = new Counter(value + 1)
  def decrement(): Counter = new Counter(value - 1)
}
