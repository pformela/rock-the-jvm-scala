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
  def map[B](transformer: MyTransformer[A, B]): MyList[B]
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]
  def filter(predicate: MyPredicate[A]): MyList[A]
  def ++[B >: A](list: MyList[B]): MyList[B]
  def printElements: String
  override def toString: String = "[" + printElements + "]"
}

object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def map[B](transformer: MyTransformer[Nothing, B]): MyList[B]= Empty
  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty
  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty
  def add[B >: Nothing](element: B): MyList[B] = new List(element, Empty)
  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
  def printElements: String = ""
}

class List[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = new List(element, this)
  def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    new List(transformer.transform(head), tail.map(transformer))
  }
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] =
    transformer.transform(head) ++ tail.flatMap(transformer)
  def filter(predicate: MyPredicate[A]): MyList[A] = {
    if(predicate.test(head)) new List(head, tail.filter(predicate))
    else tail.filter(predicate)
  }
  def ++[B >: A](list: MyList[B]): MyList[B] = new List(head, t ++ list)
  def printElements: String =
    if(t.isEmpty) "" + h
    else h.toString + " " + t.printElements
}

object ListTest {
  def main(args: Array[String]): Unit = {
    val list = new List(1, new List(2, new List(3, Empty)))
    val list2 = new List(4, new List(5, new List(6, Empty)))
    list.add(4)
    list.add(5)
    println(list.toString)

    println(list.map(new MyTransformer[Int, Int] {
      override def transform(elem: Int): Int = elem * 2
    }).toString)

    println(list.toString)

    println(list.filter(new MyPredicate[Int] {
      override def test(elem: Int): Boolean = elem % 2 == 0
    }).toString)

    println((list ++ list2).toString)

    println(list2.flatMap(new MyTransformer[Int, MyList[Int]] {
      override def transform(element: Int): MyList[Int] = new List(element, new List(element + 1, Empty))}))


  }

}



