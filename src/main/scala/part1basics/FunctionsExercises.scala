package part1basics

object FunctionsExercises extends App {
  def aGreetingFunction(name: String, age: Int) : String = {
    "Hi, my name is " + name + ". I am " + age + " years old."
  }

  def factorial(n: Int) : Int = {
    if(n <= 0) 1
    else n * factorial(n-1)
  }

  def fibonacci(n: Int) : Int = {
    if(n == 1 || n == 2) 1
    else fibonacci(n-1) + fibonacci(n-2)
  }

  def isPrime(n: Int) : Boolean = {
    def isPrimeUntil(t: Int) : Boolean = {
      if(t <= 1) true
      else n % t != 0 && isPrimeUntil(t-1)
    }

    isPrimeUntil(n / 2)
  }

  println(aGreetingFunction("John", 25))
  println(factorial(7))
  println(fibonacci(6))
  println(isPrime(22))
}
