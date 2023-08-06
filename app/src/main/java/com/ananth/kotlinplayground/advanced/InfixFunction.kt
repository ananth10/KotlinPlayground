package com.ananth.kotlinplayground.advanced

fun main(){
  val fish = Fish()
  val cow = Mammal()

  cow cross fish
}

//Infix function create rules
/**
 * 1. It must be defined inside the class or be an extension function to an existing object
 * 2. The function must be prefixed with infix notation
 * 3. The function must be contain exactly one parameter
 * */