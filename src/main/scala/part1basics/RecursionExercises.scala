package part1basics

import scala.annotation.tailrec

object RecursionExercises extends App {

  def stringConcatenation(str: String, n: Int): String = {
    @tailrec
    def stringConcHelper(str: String, n: Int, accumulator: String): String =
      if(n <= 0) accumulator
      else stringConcHelper(str, n-1, accumulator + str)

    stringConcHelper(str, n, "")
  }

  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeHelper(t: Int, isStillPrime: Boolean): Boolean =
      if(!isStillPrime) false
      else if (t <= 1) true
      else isPrimeHelper(t-1, n % t != 0 && isStillPrime)

    isPrimeHelper(n/2, true)
  }

  def fibonacci(n: Int): Int = {
    @tailrec
    def fibHelper(i: Int, last: Int, nextToLast: Int): Int =
      if(i >= n) last
      else fibHelper(i + 1, last + nextToLast, last)

    if(n <= 2) 1
    else fibHelper(2, 1, 1)
  }

  println(stringConcatenation("ab", 5))
  println(isPrime(18))
  println(fibonacci(8))

}
