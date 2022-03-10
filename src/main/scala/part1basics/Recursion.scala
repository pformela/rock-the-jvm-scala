package part1basics

object Recursion extends App {
  def factorial(n: Int): Int = {
    if(n <= 1) 1
    else n * factorial(n-1)
  }

  def anotherFactorial(n: Int): Int = {
    def factHelper(x: Int, accumulator: Int): Int =
      if(x <= 1) accumulator
      else factHelper(x-1, x * accumulator)

    factHelper(n, 1)
  }

}
