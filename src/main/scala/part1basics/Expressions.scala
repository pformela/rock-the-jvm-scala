package part1basics

import part1basics.ValuesVariablesTypes.aVariable

object Expressions extends App {

  val x = 1 + 2


  val aCondition = true
  val aConditionValue = if(aCondition) 5 else 3

  val aWeirdValue = (aVariable = 3)

  val aCodeBlock = {
    val y = 2
    val z = y + 1

    if(z > 2) "hello" else "goodbye"
  }

}
