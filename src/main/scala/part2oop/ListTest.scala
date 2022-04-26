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

case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def map[B](transformer: MyTransformer[Nothing, B]): MyList[B]= Empty
  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty
  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty
  def add[B >: Nothing](element: B): MyList[B] = List(element, Empty)
  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
  def printElements: String = ""
}

case class List[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = List(element, this)
  def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    List(transformer.transform(head), tail.map(transformer))
  }
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] =
    transformer.transform(head) ++ tail.flatMap(transformer)
  def filter(predicate: MyPredicate[A]): MyList[A] = {
    if(predicate.test(head)) List(head, tail.filter(predicate))
    else tail.filter(predicate)
  }
  def ++[B >: A](list: MyList[B]): MyList[B] = List(head, t ++ list)
  def printElements: String =
    if(t.isEmpty) "" + h
    else h.toString + " " + t.printElements
}

object ListTest {
  def main(args: Array[String]): Unit = {
    val list = List(1, List(2, List(3, Empty)))
    val list2 = List(4, List(5, List(6, Empty)))
    list.add(4)
    list.add(5)
    println(list.toString)

    println(list.map((elem: Int) => elem * 2).toString)

    println(list.toString)

    println(list.filter((elem: Int) => elem % 2 == 0).toString)

    println((list ++ list2).toString)

    println(list2.flatMap((element: Int) => List(element, List(element + 1, Empty))))


  }

}



