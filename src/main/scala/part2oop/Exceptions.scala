package part2oop

object Exceptions {

  class OverflowException extends RuntimeException
  class UnderflowException extends RuntimeException
  class MathCalculationException extends RuntimeException("Division by 0")

  val x: String = null;

  def getInt(withExceptions: Boolean): Int =
    if(withExceptions) throw new RuntimeException("No int this time")
    else 42

  def add(x: Int, y: Int): Int = {
    if(x > 0 && y > 0 && x + y < 0) throw new OverflowException
    else if(x < 0 && y < 0 && x + y > 0) throw new UnderflowException
    else x + y
  }

  def subtract(x: Int, y: Int): Int = {
    if(x > 0 && y < 0 & x - y < 0) throw new OverflowException
    else if(x < 0 && y > 0 & x - y > 0) throw new UnderflowException
    else x - y
  }

  def multiply(x: Int, y: Int): Int = {
    if(x > 0 && y > 0 && x * y < 0) throw new OverflowException
    else if(x < 0 && y < 0 && x * y < 0) throw new OverflowException
    else if(x > 0 && y < 0 && x * y > 0) throw new UnderflowException
    else if(x < 0 && y > 0 && x * y > 0) throw new UnderflowException
    else x + y
  }

  def divide(x: Int, y: Int): Int = {
    if(y == 0) throw new MathCalculationException
    else x / y
  }


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
