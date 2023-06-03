package com.ananth.kotlinplayground.higher_order_function

import com.ananth.kotlinplayground.performRequest

fun main(){
    testPerformRequest()
    filterUse()
}
/**
 * Lambdas are a great tool for building abstractions.
 * and their power is not restricted to collections and other classes in Standard library.
 * in this section will learn about higher order function
 * such as how your own function takes lambdas as arguments or return them.
 * we can see how HOF removes code duplication, and build nice abstraction.
 * and you will also become acquainted with "inline functions"- a powerful kotlin feature that removes the performance overhead associated with using lambdas
 * and enables more flexible control flow within lambdas.
 * */

//8.1 declaring Higher Order Function
/**
 * ->A higher order function is a function that takes another function as an argument or returns one.
 * ->In kotlin function can be represented as a value using lambdas or function reference
 * val print = {println("hi)} or val print = ::println()
 * ->Therefore, a higher order function is any function to which you can pass lambda or a function reference as an argument or both.
 * e.g the filter standard lib function takes predicate function as an argument and is therefore a HOF
 * list.filter{it%2==0}
 *
 * */

//8.1.1 Function types
/**
 * In order to declare a function that takes lambda as an argument, we need to know how to declare the type of corresponding parameter.
 * before we get this, we will look at simple case and store a lambda in a local variable,
 * -
 * In this case compiler infers that both sum and action variables have function types.
 * */

fun storeLambdaInVariable(){
    val sum = {x:Int,y:Int->x+y}
    println(sum(2,3))

    val action = { print("hi") }
}

fun explicitTypeDeclaration(){
    val sum:(Int,Int)->Int = {x,y->x+y}
    val action:()->Unit = { print("hi") }

    //nullable function type
    var funOrNull: ((Int, Int) -> Int)? = null
}

//Parameter names of function types
//we can specify names for parameters of function type:

fun performRequest(url:String,callback:(code:Int, content:String)->Unit){
    //making call to url
    //and callback to pass result
    callback(200,"success")
}

fun testPerformRequest(){
    val url = "https://www.google.com"
    val callback = { code:Int,content:String ->
        println(code)
        println(content)
    }
    //way 1
    performRequest(url,callback)

    //way 2
    //When you declare a lambda, you don’t have to use the same parameter names as the ones used in the function type declaration.
    // But the names improve readability of the code and can be used in the IDE for code completion.
    performRequest(url){ code:Int,content:String ->
        println(code)
        println(content)
    }
}

//8.1.2 Calling functions passed as arguments
/**
 * now you know , how to declare higher order function, now lets discuss how to implement one.
 * */
//Defining a simple higher-order function

fun TwoAndThree(operation:(Int,Int) ->Int){
    val result = operation(2,3)
    println("The result is $result")
}

fun makeCall(){
    TwoAndThree{a,b->a+b}
    TwoAndThree{a,b->a*b}
    //or
    val sum = {a:Int,b:Int->a+b}
    val multiply = {a:Int,b:Int->a*b}
    TwoAndThree(sum)
    TwoAndThree(multiply)
}

//As a more interesting example, let’s reimplement one of the most commonly used standard library functions:
// the filter function.To keep things simple, you’ll implement the filter function on String,

// Declaration of the filter function, taking a predicate as a parameter

fun String.filter(predicate:(Char)->Boolean):String{
    val sb = StringBuilder()
    for (index in indices){
        val element = get(index)
        if(predicate(element)) {
            sb.append(element)
        }
    }
    return sb.toString()
}

fun filterUse(){
    println("ab2c".filter { it in 'a'..'z' })
    //or
    val predicate = {char:Char -> char in 'a'..'z'} //we are storing lambda in a variable
    println("ab2c".filter(predicate)) //passing variable to function
}

//8.1.3. Using function types from Java