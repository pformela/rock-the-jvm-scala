package akka.playground

import scala.concurrent.Future

object ThreadModelLimitations extends App {

  class BankAccount(private var amount: Int) {
    override def toString: String = "" + amount

    def withdraw(money: Int) = this.synchronized {
      this.amount -= money
    }
    def deposit(money: Int) = this.synchronized {
      this.amount += money
    }
    def getAmount(): Int = this.amount
  }

//  val account = new BankAccount(2000)
//  for(_ <- 1 to 1000) {
//    new Thread(() => account.withdraw(1)).start()
//  }
//
//  for(_ <- 1 to 1000) {
//    new Thread(() => account.deposit(1)).start()
//  }
//  println(account.getAmount())

  // OOP encapsulation is brtoken in a multithreaded env
  // synchronization - Locks to the rescue, but introduces deadlocks, livelocks

  var task: Runnable = null
  val runningThread: Thread = new Thread(() => {
    while(true) {
      while(task == null) {
        runningThread.synchronized {
          println("[background] waiting for a task...")
          runningThread.wait()
        }
      }

      task.synchronized {
        println("[background] I have a task")
        task.run()
        task = null
      }
    }
  })

  def delegateToBackgroundThread(r: Runnable) = {
    if (task == null) task = r
    runningThread.synchronized {runningThread.notify()}
  }

  runningThread.start()
  Thread.sleep(500)
  delegateToBackgroundThread(() => println(42))
  Thread.sleep(1000)
  delegateToBackgroundThread(() => println("this should run in the background"))

  // 1M numbers in between 10 threads
  import scala.concurrent.ExecutionContext.Implicits.global
  val futures = (0 to 9)
    .map(i => 100000 * i until 100000 * (i + 1))
    .map(range => Future {
      if(range.contains(537293)) throw new RuntimeException("invalid number")
      range.sum
    })

  val sumFuture = Future.reduceLeft(futures)(_ + _)
  sumFuture.onComplete(println)

}
