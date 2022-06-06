package akka.part2actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.part2actors.ChildActors.CreditCard.{AttachToAccount, CheckStatus}
import akka.part2actors.ChildActors.Parent.{CreateChild, TellChild}

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
        context.become(withChild(childRef))
    }

    def withChild(childRef: ActorRef): Receive = {
      case TellChild(msg) =>
        childRef forward msg
    }
  }

  class Child extends Actor {
    override def receive: Receive = {
      case message => println(s"[${self.path}] I got: $message")
    }
  }


  val system = ActorSystem("ParentChildDemo")
  val parent = system.actorOf(Props[Parent](), "parent")
  parent ! CreateChild("child")
  parent ! TellChild("Hey")


  // Guardian actors
  // /system - system guardian
  // /user - user level guardian
  // / = the root guardian

  // Actor selection by actor path
  val childSelection = system.actorSelection("/user/parent/child")
  childSelection ! "I found you"

  // we cant pass mutable actor state or this reference to child actors

  object NaiveBankAccount {
    case class Deposit(amount: Int)
    case class Withdraw(amount: Int)
    case object InitializeAccount
  }

  class NaiveBankAccount extends Actor {

    import NaiveBankAccount._
    var amount = 0

    override def receive: Receive = {
      case InitializeAccount =>
        val creditCardRef = context.actorOf(Props[CreditCard](), "card")
        creditCardRef ! AttachToAccount(this) // !!
      case Deposit(funds) => deposit(funds)
      case Withdraw(funds) => withdraw(funds)
    }

    def deposit(funds: Int) =
      println(s"${self.path} depositing $funds on top of $amount")
      amount += funds
    def withdraw(funds: Int) =
      println(s"${self.path} withdrawing $funds from $amount")
      amount -= funds
  }

  object CreditCard {
    case class AttachToAccount(naiveBankAccount: NaiveBankAccount) // !!
    case object CheckStatus
  }

  class CreditCard extends Actor {
    override def receive: Receive = {
      case AttachToAccount(account) => context.become(attachedTo(account))
    }

    def attachedTo(account: ChildActors.NaiveBankAccount): Receive = {
      case CheckStatus =>
        println(s"[${self.path}] your message has been processed.")
        account.withdraw(1) // !!!!!! - actor state changed without sending messages
    }
  }


  import NaiveBankAccount._
  import CreditCard._

  val bankAccountRef = system.actorOf(Props[NaiveBankAccount](), "account")
  bankAccountRef ! InitializeAccount
  bankAccountRef ! Deposit(500)

  Thread.sleep(500)
  val ccSelection = system.actorSelection("/user/account/card")
  ccSelection ! CheckStatus

}


























