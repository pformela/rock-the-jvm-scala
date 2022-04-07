package part2oop

import scala.annotation.tailrec

abstract class MyList {
  def head: Int
  def tail: MyList
  def isEmpty: Boolean
  def add(element: Int): MyList
  def printElements: String
  override def toString: String = "[" + printElements + "]"
}

object Empty extends MyList {
  def head: Int = throw new NoSuchElementException
  def tail: MyList = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Int): MyList = new List(element, Empty)
  def printElements: String = ""
}

class List(h: Int, t: MyList) extends MyList {
  def head: Int = head
  def tail: MyList = t
  def isEmpty: Boolean = false
  def add(element: Int): MyList = new List(element, this)
  def printElements: String =
    if(t.isEmpty) "" + h
    else h + " " + t.printElements
}

object ListTest {
  def main(args: Array[String]): Unit = {
    val list = new List(1, new List(2, new List(3, Empty)))
    list.add(4)
    list.add(5)
    println(list.toString)
  }
}



