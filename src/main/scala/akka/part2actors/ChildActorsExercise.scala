package akka.part2actors
import akka.actor.{Actor, ActorRef, Props}

object ChildActorsExercise extends App {

  // Distributed word counting

  object WordCounterMaster {
    case class Initialize(nChildren: Int)
    case class WordCountTask(/* TODO */text: String)
    case class WordCountReply(/* TODO */count: Int)
  }

  class WordCounterMaster extends Actor {

    import WordCounterMaster._

    override def receive: Receive = {
      case Initialize(children: Int) =>
        val workers = (1 to children).map(x => context.actorOf(Props[WordCounterWorker](), s"worker$x")).toList
        context.become(ready(workers))
    }

    def ready(workerList: List[ActorRef]): Receive = {
      case WordCountTask(text: String) =>
        text.split("\n").toList
    }
  }

  class WordCounterWorker extends Actor {

    import WordCounterMaster._

    override def receive: Receive = {
      case WordCountTask(text: String) =>
        sender() ! WordCountReply(text.split(" ").toList.length)
    }
  }

}
