package part2oop

object Generics {

  class MyList[+A] {
    def add[B >: A](element: B): MyList[B] = ??? // B - supertype of A
  }

  class MyMap[Key, Value] {

  }

  object MyList {
    def empty[A]: MyList[A] = ???
  }

  class Animal
  class Cat extends Animal
  class Dog extends Animal

  class CovariantList[+A]

  class InvariantList[A]

  class ContravariantList[-A]

  def main(args: Array[String]): Unit = {
    val listOfIntegers = new MyList[Int]
    val listOfStrings = new MyList[String]
    val emptyListOfIntegers = MyList.empty[Int]

    // covariance
    val animal: Animal = new Cat
    val animalList: CovariantList[Animal] = new CovariantList[Cat]

    // invariance
//    val invariantAnimalList: InvariantList[Animal] = new InvariantList[Cat] error

    // contravariance
    val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]

    // bounded types
    class Cage[A <: Animal](animal: A) // A - only subtypes of Animal - upper bounded
    val cage = new Cage(new Dog)



  }
}
