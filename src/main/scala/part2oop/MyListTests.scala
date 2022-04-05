package part2oop

object MyListTests {
  def main(args: Array[String]): Unit = {
    val myList = new MyList()
    myList.add(1)
    myList.add(2)
    myList.add(3)
    myList.add(4)

    println(myList.toString)
  }
}
