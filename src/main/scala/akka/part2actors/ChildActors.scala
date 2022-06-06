package akka.part2actors

import akka.actor.{Actor, Props, ActorRef}

object ChildActors extends App {

  // Actors can create other actors by using context

  object Parent {
    case class CreateChild(name: String)
    case class TellChild(msg: String)
  }

  class Parent extends Actor {

    import Parent._

    var child: ActorRef = null
    override def receive: Receive = {
      case CreateChild(name) =>
        println(s"[${self.path}] creating child")
        // create a new actor here
        val childRef = context.actorOf(Props[Child](), name)
        child = childRef
      case TellChild(msg) =>
        if(child != null) child forward msg
    }
  }

  class Child extends Actor {
    override def receive: Receive = {
      case message => println(s"[${self.path}] I got: $message")
    }
  }

}













