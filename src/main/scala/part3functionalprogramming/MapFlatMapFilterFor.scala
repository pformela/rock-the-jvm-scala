package part3functionalprogramming

import scala.annotation.tailrec

object MapFlatMapFilterFor extends App {

  val list = List(1,2,3)
  println(list)

  // map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  val numbers = List(1,2,3,4)
  val chars = List("a", "b", "c", "d")

  def combine[A, B](l1: List[A], l2: List[B]): List[String] = {
    @tailrec
    def combineHelper[A, B](l1current: List[A], l2current: List[B], result: List[String]): List[String] = {
      if(l2current.isEmpty) result
      else if(l1current.isEmpty && l2current.nonEmpty) combineHelper(l1, l2current.tail, result)
      else combineHelper(l1current.tail, l2current, result :+ (l2current.head.toString + l1current.head.toString))
    }

    combineHelper(l1, l2, List())
  }

  val combinations = numbers.flatMap(n => chars.map(c => "" + c + n))
  val colors = List("black", "white")

  println(combinations)
  println(combine(numbers, chars))

  list.foreach(println)

  // for-comprehensions
  val forCombinations = for {
    n <- numbers if n % 2 == 0  // if guard - acts like filter
    c <- chars
    color <- colors
  } yield "" + c + n + "-" + color

  println(forCombinations)

  // syntax overload
  list.map { x =>
    x * 2
  }

  abstract class Maybe[+T] {
    def map[A, B](f: A => B): Maybe[B]
    def flatMap[A, B](f: A => B): Maybe[B]
    def filter[A](f: A => Boolean): Maybe[A]
  }


}
