package part2oop

object InheritanceAndTraits extends App {
  class Animal {
    def creatureType(): Unit = println("domestic")
    protected def eat: Unit = println("asdf")
  }

  class Cat extends Animal {
    def crunch(): Unit = {
      eat
      println("crunch")
    }
  }

  val cat = new Cat
  cat.crunch()

  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }
  // extending class with parameters
  class Adult(name: String, age: Int, idCard: String) extends Person(name, age) {}


  // overriding
//  class Dog(override val creatureType: String) extends Animal {
////    override def creatureType: Unit = println("domestic")
//    override def eat: Unit = println("crunch dog")
//  }

  class Dog(dogType: String) extends Animal {
    override def creatureType(): Unit = println(dogType)
    override def eat: Unit = println("crunch dog")
  }

  val dog = new Dog("K9")
  dog.eat

  //polymorphism
  val unknownAnimal: Animal = new Dog("K9")
//  unknownAnimal.eat


  abstract class Animal2 {
    val creatureType: String = "wild"
    def eat: Unit
  }

  class Dog2 extends Animal2 {
    override val creatureType: String = "Canine"
    def eat: Unit = println("eating")
  }

  // traits - ultimate absctract data types
  // do not have constructor parameters
  trait Carnivore {
    def eat(animal: Animal2): Unit
    val prefferedMeal: String = "fresh meat"
  }

  // multiple traits may be inherited
  // traits - behavior, abstract class = "thing"
  trait ColdBlodded
  class Crocodile extends Animal2 with Carnivore with ColdBlodded {
    override val creatureType: String = "croc"
    def eat: Unit = println("nomnomnom")
    def eat(animal: Animal2): Unit = println(s"I'm croc and I'm eating ${animal.creatureType}")
  }

  val dog2 = new Dog2
  val croc = new Crocodile

  croc.eat(dog2)

}

