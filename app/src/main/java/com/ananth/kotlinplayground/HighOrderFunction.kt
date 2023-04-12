package com.ananth.kotlinplayground

import java.lang.StringBuilder

fun main() {
    val list = (1..50).toList().filter { it % 2 == 1 }
    println(list)

    val sum = { x: Int, y: Int -> x + y }

    val sumWithType: (Int, Int) -> Int = { x, y -> x + y }

    val action = { println(42) }

    val actionWithType: () -> Unit = { println(42) }

    val canReturnNull: ((Int, Int) -> Int)? = null

    twoAndThree { a, b -> a + b }
    twoAndThree { a, b -> a * b }

    println("ab1c".filter { it in 'a'..'z' })

    val result = (1..100).toList().sumAllPrimeNumbers { it % 2 == 0 }
    println("SUMM$result")

    //default lambda
    val letters = listOf("Apple","Banana","Grapes")
    println(letters.joinToString())

    //passes lambda as an argument
    println(letters.joinToString{it.toLowerCase()})

    //Uses the named argument syntax for passing several arguments including a lambda
    println(letters.joinToString(separator = "!",postfix = "!",transform = {it.toUpperCase()}))

    //nullable parameter
    println(letters.joinToStringNullableParam (transform = null))

    //function return function

    val calculator = getShippingCostCalculator(Delivery.EXPEDITED)  //stores the returned function in a variable
    println("Shipping costs ${calculator(Order(3))}") //Invoke returned function

}


/**
 * Function that take function as an argument
 * */
//Parameter names of function types

fun performRequest(url: String, callBack: (code: Int, content: String) -> Unit) {

}

//Simple high order function

fun twoAndThree(operation: (Int, Int) -> Int) {
    val result = operation(2, 3)
    println(result)
}

//Filter Function

fun String.filter(predicate: (Char) -> Boolean): String {

    val sb = StringBuilder()
    for (index in 0 until length) {
        val element = get(index)
        if (predicate(element))
            sb.append(element)
    }
    return sb.toString()
}

fun List<Int>.sumAllPrimeNumbers(predicate: (Int) -> Boolean): Int {
    var sum = 0
    for (item in this) {
        if (predicate(item))
            sum += item
    }
    return sum
}

//default value for parameters

fun <T> Collection<T>.joinToString(separator: String = ", ", prefix: String = "", postfix: String = "", transform: (T) -> String = { it.toString() }):String {

    val result = StringBuilder(prefix)
    for ((index,element) in this.withIndex()){
        if(index>0){
            result.append(separator)
        }
        result.append(transform(element))
    }
    result.append(postfix)
    return result.toString()
}

//Using a nullable parameter of a function type

fun <T> Collection<T>.joinToStringNullableParam(separator: String = ", ", prefix: String = "", postfix: String = "", transform: ((T) -> String)? = null):String {

    val result = StringBuilder(prefix)
    for ((index,element) in this.withIndex()){
        if(index>0){
            result.append(separator)
        }
        val str = transform?.invoke(element)?:element.toString()
        result.append(str)
    }
    result.append(postfix)
    return result.toString()
}

/**
 * Function that return function
 * */

//Defining a function that returns another function

enum class Delivery {STANDARD, EXPEDITED}

class Order(val itemCount : Int)

fun getShippingCostCalculator(delivery: Delivery): (Order)->Double
{
    if(delivery== Delivery.EXPEDITED){
        return {order -> 6+2.1*order.itemCount }
    }
    return {order -> 1.2*order.itemCount }
}




