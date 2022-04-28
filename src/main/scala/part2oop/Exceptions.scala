package part2oop

object Exceptions {

  val x: String = null;

  def getInt(withExceptions: Boolean): Int =
    if(withExceptions) throw new RuntimeException("No int this time")
    else 42

  def main(args: Array[String]): Unit = {
    try {
      getInt(true)
    } catch {
      case e: RuntimeException => println("caught a Runtime Exception")
    } finally {
      println("finally")
    }

    val exception = try {
      getInt(true)
    } catch {
      case e: RuntimeException => 42
    } finally {
      println("finally")
    }

    println(exception)

  }
}
