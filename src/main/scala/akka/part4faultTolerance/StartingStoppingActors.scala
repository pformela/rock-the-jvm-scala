package akka.part4faultTolerance

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Kill, PoisonPill, Props, Terminated}

object StartingStoppingActors extends App {

  val system = ActorSystem("stoppingActorsDemo")


  object Parent {
    case class StartChild(name: String)
    case class StopChild(name: String)
    case object Stop
  }

  class Parent extends Actor with ActorLogging {

    import Parent._

    override def receive: Receive = {
      withChildren(Map())
    }

    // using context.stop()
    def withChildren(children: Map[String, ActorRef]): Receive = {
      case StartChild(name) =>
        log.info(s"Starting child $name")
        context.become(withChildren(children + (name -> context.actorOf(Props[Child](), name))))
      case StopChild(name) =>
        log.info(s"Stopping child with the name $name")
        val childOption = children.get(name)
        childOption.foreach(childRef => context.stop((childRef)))
      case Stop =>
        log.info("Stopping myself")
        context.stop(self)
      case message => log.info(message.toString)
    }
  }

  class Child extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(message.toString)
    }
  }

  import Parent._
//  val parent = system.actorOf(Props[Parent](), "parent")
//  parent ! StartChild("child1")
//  val child = system.actorSelection("/user/parent/child1")
//  child ! "Hi kid"
//  parent ! StopChild("child1")
//
////  for(_ <- 1 to 50) child ! "Are you still there"
//  parent ! StartChild("child2")
//  val child2 = system.actorSelection("/user/parent/child2")
//  child2 ! "Hi second child"
//  parent ! Stop

  // using special messages
  val looseActor = system.actorOf(Props[Child](), "looseChild")

//  looseActor ! "Hello, loose actor"
//  looseActor ! PoisonPill // will invoke stopping procedure
//  looseActor ! "looseActor are you still there"
//
//  val abruptlyTerminatedActor = system.actorOf(Props[Child]())
//  abruptlyTerminatedActor ! Kill
//  abruptlyTerminatedActor ! "you have been terminated"

  // Death Watch
  class Watcher extends Actor with ActorLogging {

    import Parent._

    override def receive: Receive = {
      case StartChild(name) =>
        val child = context.actorOf(Props[Child](), name)
        log.info(s"Starting and watching child $name")
        context.watch(child) // register for death watch
      case Terminated(ref) => // actor reference that has just been stopped
        log.info(s"The reference that I am watching $ref has been stopped")
    }

  }

  val watcher = system.actorOf(Props[Watcher](), "watcher")
  watcher ! StartChild("watchedChild")
  val watchedChild = system.actorSelection("/user/watcher/watchedChild")

  Thread.sleep(500)

  watchedChild ! PoisonPill

}
