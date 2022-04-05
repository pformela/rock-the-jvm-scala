package part3functionalprogramming

import scala.annotation.tailrec

object HOFsAndCurries extends App {
  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = null

  // higher order functions - map, flatMap, filter etc.

  // function that applies a function n times over a value x
  def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if(n <= 0) x
    else nTimes(f, n-1, f(x))

  def plusOne = (x: Int) => x + 1

  println(nTimes(plusOne, 10, 1))


  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) =
    if(n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n-1)(f(x))

  val plus10 = nTimesBetter(plusOne, 10)
  println(plus10(7))

  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) = {
    x => y => f(x, y)
  }
  def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int =
    (x, y) => f(x)(y)

}
