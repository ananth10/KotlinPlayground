package com.ananth.kotlinplayground.lambdas

import com.ananth.kotlinplayground.people
fun salute() = println("Salute!")

fun main(){

}
//Member Reference

/**
 *we have seen how lambda allow us to pass a block of code as a parameter to a function,
 * but what if the code that you need to pass as a parameter is already defined as a function?,
 * of course you can pass lambda that calls that function, but doing so somewhat redundant,
 * can you pass function directly?
 * In Kotlin, just like in Java 8, you can do so if you convert the function to a value
 *
 * You use the :: operator for that:
 *
 *
 * */

fun testMember(){
    val age = Person::age //This expression is called member reference, it provides a short syntax for creating a function value
     //not short hand
    val getAge = { person: Person -> person.age }
    //short hand
    val getAge1 = {Person::age }

//    people.maxBy(Person::age)

//to call top level function
    run(::salute)
}

fun sendMail(person:Person,message:String){
    println("Message sent")
}

fun testMember1(){
    val action = {person:Person, message:String-> sendMail(person,message) }

    val nextAction = ::sendMail

}

//we can store or postpone the action of creating an instance of a class using a constructor reference.
//The constructor reference is formed by specifying the class after double colons

data class Employee(val name:String, val location:String)

fun createEmp(){
    val createEmp = ::Employee
    val emp = createEmp("john","maimi")
    println(emp)

    println(Person::isAdult)
}
//Note that you can also reference extension functions the same way:

fun Person.isAdult() = age>20
