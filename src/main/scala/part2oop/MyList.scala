package part2oop

import scala.annotation.tailrec
import scala.reflect.ClassManifestFactory.Nothing

//class Node(val x: Int, val next: Node = null) {
//  def setNext(nextNode: Node): Node = {
//    new Node(this.x, nextNode)
//  }
//}

class MyList(head: Option[Int] = None, tail: MyList = null) {

  def add(item: Int): MyList = {
    if(head == null) new MyList(Some(item), new MyList())
    else {
//      val currNode = this.head
//
//      new MyList(this.head, new MyList())
      this
    }
  }
  def isEmpty: Boolean = head == null
//  override def toString: String = {
//    @tailrec
//    def toStringHelper(currNode: Node, acc: String): String = {
//      if(currNode == null) acc
//      else toStringHelper(currNode.next, acc + " -> " + currNode.x)
//    }
//
//    toStringHelper(this.head, "")
//  }
}


