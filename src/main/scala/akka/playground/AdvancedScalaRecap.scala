package akka.playground

import scala.concurrent.Future

object AdvancedScalaRecap {

  // partial functions
  val partialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 65
    case 5 => 999
  }

  val pf = (x: Int) => x match {
    case 1 => 42
    case 2 => 65
    case 5 => 999
  }

  val function: (Int => Int) = partialFunction

  val modifiedList = List(1,2,3).map({
    case 1 => 42
    case _ => 0
  })

  // lifting
  val lifted = partialFunction.lift // total function Int => Option[Int]
  lifted(2) // Some(65)
  lifted(5000) // None

  // orElse
  val pfChain = partialFunction.orElse[Int, Int] {
    case 60 => 9000
  }

  pfChain(5) // 999 per partialFunction
  pfChain(60) // 9000
  pfChain(457) // throw a Matcher

  // type aliases
  type ReceiveFuntion = PartialFunction[Any, Unit]

  def receive: ReceiveFuntion = {
    case 1 => println("hello")
    case _ => println("confused...")
  }

  // implicits

  implicit val timeout: Int = 3000
  def setTimeout(f: () => Unit)(implicit timeout: Int) = f()

  setTimeout(() => println("timeout"))

  // implicit conversions
  // 1) implicit defs
  case class Person(name: String) {
    def greet = s"Hi, my name is $name"
  }

  implicit def fromStringToPerson(string: String): Person = Person(string)
  "Peter".greet // equivalent to fromStringToPerson("Peter").greet

  implicit class Dog(name: String) {
    def bark = println("bark!")
  }
  "Lassie".bark // equivalent to new Dog("Lassie").bark - automatically done by compiler

  // organize
  // local scope
  implicit val inverseOrder: Ordering[Int] = Ordering.fromLessThan(_ > _)
  List(1,2,3).sorted // sorter in reverse order by the implicit val

  // imported scope
  import scala.concurrent.ExecutionContext.Implicits.global
  val future = Future {
    println("hello future")
  }

  // companion objects of the types included in the call
  object Person {
    implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  List(Person("Bob"), Person("Alice")).sorted // List(Person("Alice"), Person("Bob"))
}
