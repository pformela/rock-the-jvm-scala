package akka.part2actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorCapabilities extends App {

  class SimpleActor extends Actor {
    override def receive: Receive = {
      case "Hi" => context.sender() ! "Hello there" // context.sender returns ActorRef
      case message: String => println(s"[$self] I have received $message")
      case number: Int => println(s"[simpleActor] I have received a number $number")
      case SpecialMessage(contents) => println(s"[simpleActor] I have received something special $contents")
      case SendMessageToYourself(contents) =>
        self ! contents
      case SayHiTo(ref) => ref ! "Hi"
      case WirelessPhoneMessage(content, ref) => ref.forward(content + "s")
    }
  }

  val system = ActorSystem("actorCapabilitiesDemo")
  val simpleActor = system.actorOf(Props[SimpleActor](), "simpleActor")

  simpleActor ! "hello actor"
  simpleActor ! 42

  // any message can be sent under some conditions
  // messages must be immutable
  // messages must be serializable

  // in practice use case classes and case objects

  case class SpecialMessage(contents: String)
  simpleActor ! SpecialMessage("some special content")

  // actors have information about their context and about themselves
  // context.self == this

  case class SendMessageToYourself(context: String)
  simpleActor ! SendMessageToYourself("I am an actor and I am proud of it")

  // actors can reply to messages
  val alice = system.actorOf(Props[SimpleActor](), "alice")
  val bob = system.actorOf(Props[SimpleActor](), "bob")

  case class SayHiTo(ref: ActorRef)
  alice ! SayHiTo(bob)


  // forwarding messages - sending messages with original sender

  case class WirelessPhoneMessage(content: String, ref: ActorRef)
  alice ! WirelessPhoneMessage("hi", bob)

  object Counter {
    case object Increment
    case object Decrement
    case object Print
  }

  class CounterActor extends Actor {
    import Counter._
    var counter = 0
    override def receive: Receive = {
      case Increment => this.counter += 1
      case Decrement => this.counter -= 1
      case Print => println(s"[counterActor] Counter value: $counter")
    }
  }

  import Counter._
  val counterActor = system.actorOf(Props[CounterActor](), "counterActor")
  counterActor ! Increment
  counterActor ! Increment
  counterActor ! Increment
  counterActor ! Print
  counterActor ! Decrement
  counterActor ! Print

  object BankAccount {
    case class BankRequest(accountBalance: Int, difference: Int)
    case class Withdraw(amount: Int)
    case class Deposit(amount: Int)
  }

  class BankActor extends Actor {
    import BankAccount._
    override def receive: Receive = {
      case BankRequest(accBal, diff) if diff < 0 && accBal + diff < 0 => println("Failure")
      case BankRequest(accBal, diff) if diff < 0 && accBal + diff >= 0 =>
        context.sender() ! Withdraw(diff)
        println("Success")
      case BankRequest(accBal, diff) if diff > 0 =>
        context.sender() ! Deposit(diff)
        println("Success")
      case BankRequest(accBal, diff) if diff == 0 => println("Failure")
    }
  }

  val bankActor = system.actorOf(Props[BankActor](), "bankActor")

  class BankAccountActor extends Actor {
    import BankAccount._
    var accountBalance = 0
    override def receive: Receive = {
      case number: Int => bankActor ! BankRequest(this.accountBalance, number)
      case Withdraw(amount) => this.accountBalance -= amount
      case Deposit(amount) => this.accountBalance += amount
      case "Statement" => println(s"[bankAccountActor] Current balance: $accountBalance")
    }
  }


  val myBankAccountActor = system.actorOf(Props[BankAccountActor](), "myBankAccountActor")
  myBankAccountActor ! 300
  myBankAccountActor ! 0
  myBankAccountActor ! -400
  myBankAccountActor ! 100
  myBankAccountActor ! "Statement"
}





















