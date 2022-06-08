package akka.part2actors

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.event.Logging

object ActorLogging extends App {
  class SimpleActorWithExplicitLogger extends Actor {
    // explicit logging
    val logger = Logging(context.system, this)

    override def receive: Receive = {
      case message => logger.info(message.toString)
    }
  }

  val system = ActorSystem("loggingDemo")
  val actor = system.actorOf(Props[SimpleActorWithExplicitLogger]())

  actor ! "Logging a simple message"

  // Actor Logging
  class ActorWithLogging extends Actor with ActorLogging {
    override def receive: Receive = {
      case (a, b) => log.info("Two things: {} and {}", a, b)
      case message => log.info(message.toString)
    }
  }

  val simpleActor = system.actorOf(Props[ActorWithLogging]())
  simpleActor ! "Logging a simple message by extending a trait"
  simpleActor ! (2, 3)

}
