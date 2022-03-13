package part1basics

object CalledByNameVsValue extends App {

  def calledByValue(x: Long): Unit = {
    print("by value: " + x)
    print("by value: " + x)
  }

  def calledByName(x: => Long): Unit = {
    print("by name: " + x)
    print("by name: " + x)
  }

  calledByValue(System.nanoTime())
  calledByName(System.nanoTime())

}
