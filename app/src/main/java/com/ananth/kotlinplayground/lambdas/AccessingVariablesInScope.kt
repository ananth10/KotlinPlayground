package com.ananth.kotlinplayground.lambdas

import com.ananth.kotlinplayground.generics.list


fun main(){
    testScope()
    testScope1()
}
//accessing variables in scope:
/**
 * -> You know that when you declare an anonymous inner class in a function,
 * ->you can refer to parameters and local variables of that function from inside the class
 * ->With lambdas, you can do exactly the same thing.
 * If you use a lambda in a function, you can access the parameters of that function as well as the local variables declared before the lambda.
 * To demonstrate this, let’s use the forEach standard library function.
 *
 * */

//Using function parameters in a lambda

fun printMessageWithPrefix(messages: Collection<String>, prefix: String) {
    messages.forEach {
        println("$prefix$it")
    }
}

fun testScope(){
    val list = listOf("403 forbidden","404 not found")
    printMessageWithPrefix(list,"Error:")
}

//One important difference between Kotlin and Java is that in Kotlin, you aren’t restricted to accessing final variables.
//You can also modify variables from within a lambda.

//Changing local variables from a lambda

fun printProblemCount(response:Collection<String>){
    var clientErrors=0
    var serverError=0

    response.forEach {
        if(it.startsWith("4")){
            clientErrors++
        }else{
            serverError++
        }
    }

    println("$clientErrors client errors, $serverError server errors")
}

fun testScope1(){
    val list = listOf("200 OK","418 I am a teapot", "500 server errors")
    printProblemCount(list)
}

//mutate variable
fun testMutate(){
    var count = 0
    val inc = {count++}
}