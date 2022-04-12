package part2oop

object AnonymousClasses {

  abstract class Animal {
    def eat: Unit
  }

  class Person(name: String) {
    def sayHi(): Unit = println(s"Elo, $name")
  }

  trait MyPredicate[-T] {
    def test(element: T): Boolean
  }
  
  trait MyTransformer[-A, B] {
    def transform(element: A): B
  }
  

  def main(args: Array[String]): Unit = {
    val funnyAnimal: Animal = new Animal {
      override def eat: Unit = println("lol")
    }

    val jeff = new Person("Jeff") {
      override def sayHi(): Unit = super.sayHi()
    }
  }
}
