package akka.part2actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.part2actors.ChangingActorBehaviour.Counter.{Decrement, Increment, Print}
import akka.part2actors.ChangingActorBehaviour.Mom.{Food, MomStart}
import scala.collection.immutable.HashMap

object ChangingActorBehaviour extends App {


  object FussyKid {
    case object KidAccept
    case object KidReject
    val HAPPY = "happy"
    val SAD = "sad"
  }

  class FussyKid extends Actor {

    import FussyKid._
    import Mom._

    // internal state
    var state = HAPPY
    override def receive: Receive =
      case Food(VEGETABLE) => state = SAD
      case Food(CHOCOLATE) => state = HAPPY
      case Ask(msg) =>
        if(state == HAPPY) sender() ! KidAccept
        else sender() ! KidReject
  }


  class StatelessFussyKid extends Actor {

    import FussyKid._
    import Mom._

    override def receive: Receive = happyReceive

    def happyReceive: Receive = {
      case Food(VEGETABLE) => // change receive to sadReceive
        context.become(sadReceive, false)
      case Food(CHOCOLATE) =>
      case Ask(_) => sender() ! KidAccept
    }
    def sadReceive: Receive = {
      case Food(VEGETABLE) => // stay sad
        context.become(sadReceive, false)
      case Food(CHOCOLATE) =>  // change receive to happy
        context.unbecome()
      case Ask(_) => sender() ! KidReject
    }
  }

  object Mom {
    case class MomStart(kidRef: ActorRef)
    case class Food(food: String)
    case class Ask(msg: String)
    val VEGETABLE = "veggies"
    val CHOCOLATE = "chocolate"
  }

  class Mom extends Actor {

    import Mom._
    import FussyKid._

    override def receive: Receive =
      case MomStart(kidRef) =>
        kidRef ! Food(VEGETABLE)
        kidRef ! Ask("Do you want to play?")
      case KidAccept => println("Yay, my kid is happy")
      case KidReject => println("My kid is sad, but at least healthy")
  }

  val system = ActorSystem("changingActorBehaviourDemo")
  val fussyKid = system.actorOf(Props[FussyKid](), "kid")
  val statelessFussyKid = system.actorOf(Props[StatelessFussyKid](), "statelessKid")
  val mom = system.actorOf(Props[Mom](), "mom")

//  mom ! MomStart(statelessFussyKid)

  // Exercises

  object Counter {
    case object Increment
    case object Decrement
    case object Print
  }

  class Counter extends Actor {

    import Counter._

    override def receive: Receive = {
      case Decrement => context.become(currentState(-1), false)
      case Increment => context.become(currentState(1), false)
      case Print => println("Current state: 0")
    }

    def currentState(num: Int): Receive = {
      case Increment => context.become(currentState(num + 1), false)
      case Decrement => context.become(currentState(num - 1), false)
      case Print => println("Current state: " + num)
    }
  }

  val counterSystem = ActorSystem("counterSystem")
  val counter = counterSystem.actorOf(Props[Counter](), "counter")

  counter ! Print
  counter ! Increment
  counter ! Increment
  counter ! Increment
  counter ! Print
  counter ! Decrement
  counter ! Decrement
  counter ! Print


  case class Vote(candidate: String)
  case object VoteStatusRequest
  case class VoteStatusReply(candidate: Option[String])

  class Citizen extends Actor {
    override def receive: Receive =
      case Vote(candidate) =>
        context.become(alreadyVoted(candidate))
      case VoteStatusRequest =>
        sender() ! VoteStatusReply(None)

    def alreadyVoted(candidate: String): Receive =
      case VoteStatusRequest =>
        sender() ! VoteStatusReply(Some(candidate))
      case Vote(candidate) =>
  }

  case class AggregateVotes(citizens: Set[ActorRef])

  class VoteAggregator extends Actor {
    override def receive: Receive =
      case AggregateVotes(citizens: Set[ActorRef]) =>
        context.become(countVotes(citizens.toList.tail, new HashMap[String, Int]()))
        citizens.toList.head ! VoteStatusRequest

    def countVotes(citizens: List[ActorRef], votes: HashMap[String, Int]): Receive = {
      case VoteStatusReply(candidate: Option[String]) =>
        if (citizens.isEmpty) {
          println(votes)
          context.unbecome()
        } else if (candidate.getOrElse("x") == "x") {
          context.become(countVotes(citizens.tail, votes))
          citizens.head ! VoteStatusRequest
        } else if (votes.contains(candidate.get)) {
          context.become(countVotes(citizens.tail, votes + (candidate.get -> (votes(candidate.get) + 1))))
          citizens.head ! VoteStatusRequest
        } else {
          context.become(countVotes(citizens.tail, votes + (candidate.get -> 1)))
          citizens.head ! VoteStatusRequest
        }
    }
  }

  val votingSystem = ActorSystem("votingSystem")
  val alice = votingSystem.actorOf(Props[Citizen](), "alice")
  val bob = votingSystem.actorOf(Props[Citizen](), "bob")
  val charlie = votingSystem.actorOf(Props[Citizen](), "charlie")
  val daniel = votingSystem.actorOf(Props[Citizen](), "daniel")

  val citizens = Set(alice, bob, charlie, daniel)

  val voteAggregator = votingSystem.actorOf(Props[VoteAggregator](), "voteAggregator")

  alice ! Vote("Richard")
  bob ! Vote("Alexander")
  charlie ! Vote("Richard")

  voteAggregator ! AggregateVotes(citizens)

}












