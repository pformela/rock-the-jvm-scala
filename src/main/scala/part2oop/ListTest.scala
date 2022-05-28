package part2oop

import scala.annotation.tailrec

trait MyPredicate[-T] {
  def test(element: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(element: A): B
}

abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def map[B](transformer: A => B): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]
  def ++[B >: A](list: MyList[B]): MyList[B]
  def printElements: String
  def foreach(func: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyList[A]
  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]
  def fold[B](start: B)(operator: (B, A) => B): B
  override def toString: String = "[" + printElements + "]"
}

case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def map[B](transformer: Nothing => B): MyList[B]= Empty
  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty
  def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty
  def add[B >: Nothing](element: B): MyList[B] = List(element, Empty)
  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
  def printElements: String = ""
  def foreach(func: Nothing => Unit): Unit = ()
  def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = Empty
  def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] =
    if(!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Empty
  def fold[B](start: B)(operator: (B, Nothing) => B): B =
    start
}

case class List[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = List(element, this)
  def map[B](transformer: A => B): MyList[B] = {
    List(transformer(head), tail.map(transformer))
  }
  def flatMap[B](transformer: A => MyList[B]): MyList[B] =
    transformer(head) ++ tail.flatMap(transformer)
  def filter(predicate: A => Boolean): MyList[A] = {
    if(predicate(head)) List(head, tail.filter(predicate))
    else tail.filter(predicate)
  }
  def ++[B >: A](list: MyList[B]): MyList[B] = List(head, t ++ list)
  def printElements: String =
    if(t.isEmpty) "" + h
    else h.toString + " " + t.printElements

  override def foreach(func: A => Unit): Unit =
    func(head)
    t.foreach(func)

  override def sort(compare: (A, A) => Int): MyList[A] =
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if (sortedList.isEmpty) List(x, Empty)
      else if (compare(x, sortedList.head) <= 0) List(x, sortedList)
      else List(sortedList.head, insert(x, sortedList.tail))

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)

  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] =
    if(list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else List(zip(head, list.head), t.zipWith(list.tail, zip))

  def fold[B](start: B)(operator: (B, A) => B): B =
    tail.fold(operator(start, head))(operator)
}

object ListTest {
  def main(args: Array[String]): Unit = {
    val list = List(1, List(2, List(3, Empty)))
    val list2 = List(4, List(5, List(6, Empty)))
    list.add(4)
    list.add(5)
    println(list.toString)

    println(list.map(_ * 2).toString)

    println(list.toString)

    println(list.filter(_ % 2 == 0).toString)

    println((list ++ list2).toString)

    println(list2.flatMap((element: Int) => List(element, List(element + 1, Empty))))

    for {
      num <- list
    } println(num)

  }

}



