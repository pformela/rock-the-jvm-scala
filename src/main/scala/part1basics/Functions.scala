package part1basics

object Functions extends App {
  def aFunction(a: String, b: Int) : String =
    a + " " + b

  println(aFunction("hello", 9876))

  def aParameterlessFunction() : Int = 42

  println(aParameterlessFunction())

  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + "\n" + aRepeatedFunction(aString, n-1)
  }

  println(aRepeatedFunction("elo", 7))

  def aFunctionWithSideEffects(aString: String) : Unit =
    println(aString)

  def aBigFunction(n: Int) : Int = {
    def aSmallerFunction(a: Int, b: Int) : Int = a + b

    aSmallerFunction(n, n-1)
  }
}
