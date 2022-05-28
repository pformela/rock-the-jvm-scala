package akka.playground

import scala.concurrent.Future
import scala.util.Success
import scala.util.Failure

object MultiThreadingRecap extends App {

  val aThread = new Thread(() => println("I am running in a parallel"))
  aThread.start()
  aThread.join()

  val threadHello = new Thread(() => (1 to 1000).foreach(_ => println("Hello")))
  val threadGoodbye = new Thread(() => (1 to 1000).foreach(_ => println("Goodbye")))

  threadHello.start()
  threadGoodbye.start()

  // different runs produce different results
  class BankAccount(@volatile private var amount: Int) { // @volatile locks for read and write on only one thread
    override def toString: String = "" + amount

    def withdraw(money: Int) = this.amount -= money
    def safeWithdraw(money: Int) = this.synchronized( // synchronized locks the instruction for only one thread
      this.amount -= money
    )
  }

  // inter-thread communication on the JVM
  // wait - notify mechanism

  // Scala Futures
  import scala.concurrent.ExecutionContext.Implicits.global
  val future = Future {
    // long computation - on different thread
    42
  }

  // callbacks
  future.onComplete { // Run something on complete
    case Success(42) => println("I found 42")
    case Failure(_) => println("something happened with the meaning of life!")
  }

  val aProcessedFuture = future.map(_ + 1) // Future with 43
  val aFlatFuture = future.flatMap { value =>
    Future(value + 2)
  } // Future with 44

  val filteredFuture = future.filter(_ % 2 == 0) // if doesnt fit, no such element exception

  // for comprehensions
  val aNonSenseFuture = for {
    meaningOfLige <- future
    filteredMeaning <- filteredFuture
  } yield meaningOfLige + filteredMeaning

  // andThen, recoverWith

  // Promises

}
