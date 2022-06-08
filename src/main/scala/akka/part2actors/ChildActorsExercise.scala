package akka.part2actors
import akka.actor.{Actor, ActorRef, Props, ActorSystem}

object ChildActorsExercise extends App {

  // Distributed word counting

  object WordCounterMaster {
    case class Initialize(nChildren: Int)
    case class WordCountTask(id: Int, text: String)
    case class WordCountReply(id: Int, count: Int)
  }

  class WordCounterMaster extends Actor {

    import WordCounterMaster._

    override def receive: Receive = {
      case Initialize(children: Int) =>
        println("[master] initializing")
        val workers = (1 to children).map(x => context.actorOf(Props[WordCounterWorker](), s"worker$x")).toList
        context.become(ready(workers, 0, 0, Map[Int, ActorRef]()))
    }

    def ready(workerList: List[ActorRef], currChild: Int, currTaskId: Int, requestMap: Map[Int, ActorRef]): Receive = {
      case text: String =>
        println(s"[master] I have received $text - I will send it to child $currChild")
        val originalSender = sender()
        val task = WordCountTask(currTaskId, text)
        val childRef = workerList(currChild)

        val newRequestMap = requestMap + (currTaskId -> originalSender)

        childRef ! task
        context.become(ready(workerList, (currChild + 1) % workerList.length, currTaskId + 1, newRequestMap))
      case WordCountReply(id: Int, number: Int) =>
        println(s"[master] I have received reply for task $id with $number")
        val originalSender = requestMap(id)
        context.become(ready(workerList, currChild, currTaskId, requestMap - id))
    }
  }

  class WordCounterWorker extends Actor {

    import WordCounterMaster._

    override def receive: Receive = {
      case WordCountTask(id: Int, text: String) =>
        println(s"${self.path} I have received a task $id with $text")
        sender() ! WordCountReply(id, text.split(" ").toList.length)
    }
  }

  class TestActor extends Actor {

    import WordCounterMaster._

    override def receive: Receive = {
      case "go" =>
        val master = context.actorOf(Props[WordCounterMaster](), "master")
        master ! Initialize(3)
        val texts = List("I love akka", "Scala is super dope", "yes", "me too")
        texts.foreach(text => master ! text)
    }
  }

  val system = ActorSystem("roundRobinWordCountExercise")
  val testActor = system.actorOf(Props[TestActor](), "testActor")

  testActor ! "go"

}
