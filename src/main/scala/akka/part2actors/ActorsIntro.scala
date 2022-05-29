package akka.part2actors

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props

object ActorsIntro extends App {

  // part1 - actor system
  val actorSystem = ActorSystem("firstActorSystem") // recommended to have one actor system per app
  println(actorSystem.name)

  // part2 - create actors
  // actors are uniquely identified within an actor system
  // messages are processes asynchronously
  // each actor has unique way of processing a message
  // cant invade other actor - force them to give information

  // word count actor

  class WordCountActor extends Actor {
    // internal data
    var totalWords = 0

    // behaviour
    def receive: PartialFunction[Any, Unit] = {
      case message: String =>
        println(s"[wordCounter] I have received a message: $message")
        totalWords += message.split(" ").length
      case msg => println(s"[wordCounter] I cannot understand ${msg.toString}")
    }
  }

  // part3 - instantiate our actor
  // you need to have a system and then call systemOf, which takes an argument
  // with Props object, that has a type of out actor, and the last parameter is actor name
  val wordCounter = actorSystem.actorOf(Props[WordCountActor](), "wordCounter")
  val anotherWordCounter = actorSystem.actorOf(Props[WordCountActor](), "anotherWordCounter")
  // part4 - communicate - invoking method by exclamation mark with infix notation
  wordCounter ! "I am learning akka and it's cool"
  anotherWordCounter ! "A different message"
  // message is completely asynchronous
  // actors can not be poked with data, we cant call idt methods and we cant instantiate them
  // the only way to communicate with actors is by exclamation mark
  // every actor has a receive method which defines its behaviour

  // other way of instantiaging

  // best practice with creating actors
  object Person {
    def props(name: String) = Props(new Person(name)) // factory method creates props with name
  }

  class Person(name: String) extends Actor {
    override def receive: Receive = {
      case "hi" => println(s"Hi, my name is $name")
    }
  }
  val person = actorSystem.actorOf(Person.props("Bob")) // not the best practice
  person ! "hi"


}
