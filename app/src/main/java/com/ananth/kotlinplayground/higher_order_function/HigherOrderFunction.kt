package com.ananth.kotlinplayground.higher_order_function

import com.ananth.kotlinplayground.OS
import com.ananth.kotlinplayground.performRequest
import java.util.concurrent.locks.Lock

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
/**
 * ->Under the hood, function types are declared as regular interfaces:
 * a variable of a function type is an implementation of a FunctionN interface.
 * ->The Kotlin standard library defines a series of interfaces, corresponding to different numbers of function arguments:
 * Function0<R> (this function takes no arguments)
 * Function1<P1, R> (this function takes one argument), and so on
 *  Each interface defines a single invoke method,and calling it will execute the function.
 *  A variable of a function type is an instance of a class implementing the corresponding FunctionN interface
 *  with the invoke method containing the body of the lambda.
 * */

//Kotlin functions that use function types can be called easily from Java. Java 8 lambdas are automatically converted to values of function types:

/* Kotlin declaration */
fun processTheAnswer(f: (Int) -> Int) {
    println(f(42))
}
/* Java */
//processTheAnswer(number -> number + 1);

//In older Java versions, you can pass an instance of an anonymous class implementing the invoke
//method from the corresponding function interface:


/* Java */
//processTheAnswer(
//new Function1<Integer, Integer>() {
//@Override
// public Integer invoke(Integer number) {
//System.out.println(number);
//return number + 1;
//}
//});

//Default and null values for parameters with function types
/**
 * when we declare a parameter of function type, you can also specify its default value.
 * To see where this can be useful, lets go back to the "joinToString" that we discussed.
 * ->This implementation is flexible, but it doesn’t let you control one key aspect of the conversion:
 *  how individual values in the collection are converted to strings.
 *  The code uses StringBuilder.append(o: Any?), which always converts the object to a string using the toString method.
 *  ->You now know that you can pass a lambda to specify how values are converted into strings
 *  But requiring all callers to pass that lambda would be cumbersome,  because most of them are OK with the default behavior.
 *  To solve this, you can define a parameter of a function type and specify a default value for it as a lambda.
 * */

//joinToString with hard-coded toString conversion

fun <T> Collection<T>.joinStoString(
    separator:String=", ",
    prefix:String="",
    postFix:String =""
):String{
    val result = StringBuilder(prefix)
    for((index,element) in this.withIndex()){
        if(index>0)
            result.append(separator)
        result.append(element)
    }
    result.append(postFix)
    return result.toString()
}

fun <T> Collection<T>.joinStoString1(
    separator:String=", ",
    prefix:String="",
    postFix:String ="",
    transform:(T)->String = {it.toString()}
):String{
    val result = StringBuilder(prefix)
    for((index,element) in this.withIndex()){
        if(index>0)
            result.append(separator)
        result.append(transform(element))
    }
    result.append(postFix)
    return result.toString()
}

//nullable function type

fun foo(callback:(()->Unit)?){
    if (callback != null) {
        callback()
    }
}

//A shorter version makes use of the fact that a function type is an implementation of an interface with an invoke method.
//As a regular method, invoke can be called through the safe-call syntax: callback?.invoke().
fun foo1(callback:(()->Unit)?){
    if (callback != null) {
        callback?.invoke()
    }
}

//8.1.5. Returning functions from functions
/**
 * The requirement to return a function from another function doesn’t come up as often as passing,functions to other functions,
 * but it’s still useful. For instance, imagine a piece of logic in a program that can vary depending on the state of the program or other conditions
 * ->for example, calculating the cost of shipping depending on the selected shipping method.
 * You can define a function that chooses the appropriate logic variant and returns it as another function.
 * */

//Listing 8.6. Defining a function that returns another function

enum class Delivery{
    STANDARD,
    EXPEDITED
}

class Order(val itemCount:Int){
    fun getShippingCostCalculator(delivery: Delivery): (Order)->Double{
     if(delivery==Delivery.EXPEDITED){
         return {order: Order -> 6+2.1 * order.itemCount }
     }
        return {order: Order -> 1.2 * order.itemCount }
    }

    fun calculator(){
        val calculator = getShippingCostCalculator(Delivery.EXPEDITED)
        println("Shipping costs ${calculator(Order(3))}")
    }
}

//8.1.6. Removing duplication through lambdas
/**
 * ->Function types and lambda expressions together constitute a great tool to create reusable code.
 * */

data class SiteVisit(val path:String,val duration:Double,val os: OS)

enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }

val log = listOf(
    SiteVisit("/", 34.0, OS.WINDOWS),
    SiteVisit("/", 22.0, OS.MAC),
    SiteVisit("/login", 12.0, OS.WINDOWS),
    SiteVisit("/signup", 8.0, OS.IOS),
    SiteVisit("/", 16.3, OS.ANDROID)
)

//Imagine that you need to display the average duration of visits from Windows machines. You can perform the task using the average function.

fun getAvgDurationForWinUser(){
    val averageDuration = log.filter { it.os==OS.WINDOWS }.map { it.duration }.average()
}

//Now, suppose you need to calculate the same statistics for Mac users. To avoid duplication, you can extract the platform as a parameter.


//Removing duplication with a regular function
//Note how making this function an extension improves readability.
//But it’s not powerful enough.
// Imagine that you’re interested in the average duration of visits from the mobile platforms (currently you recognize two of them: iOS and Android).
fun List<SiteVisit>.averageDurationFor(os:OS) = this.filter { it.os==os }.map { it.duration }.average()

fun getAvgDurationForMobileVisits(){
    val averageDuration =
        log.filter { it.os in setOf(OS.ANDROID, OS.IOS) }.map { it.duration }.average()
}
//Now a simple parameter representing the platform doesn’t do the job
//It’s also likely that you’ll want to query the log with more complex conditions,
//such as “What’s the average duration of visits to the signup page from iOS?”
//Lambdas can help. You can use function types to extract the required condition into a parameter.

//Removing duplication with a higher-order function

fun List<SiteVisit>.averageDurationFor(predicate:(SiteVisit)->Boolean) = filter(predicate).map{it.duration}.average()
fun testSiteVisit(){
    println(log.averageDurationFor { it.os in setOf(OS.IOS,OS.ANDROID) })

    println(log.averageDurationFor { it.os ==OS.IOS && it.path=="/signup" })
}
//Function types can help eliminate code duplication. If you’re tempted to copy and paste a piece of the code,
//it’s likely that the duplication can be avoided.
//With lambdas, you can extract not only the data that’s repeated, but the behavior as well.

/**
 * Some well-known design patterns can be simplified using function types and lambda expressions.
 * Let’s consider the Strategy pattern, for example. Without lambda expressions,
 * it requires you to declare an interface with several implementations for each possible strategy.
 * With function types in your language, you can use a general function type to describe the strategy, and pass different lambda expressions as different strategies.
 * */

//Inline functions: removing the overhead of lambdas
/**
 * -> we explained that lambdas are normally compiled to anonymous classes.
 * -> But that means every time you use a lambda expression, an extra class is created;
 * -> and if the lambda captures some variables, then a new object is created on every invocation.
 * ->This introduces runtime overhead, causing an implementation that uses a lambda to be less efficient than a function that executes the same code directly.
 * ->Could it be possible to tell the compiler to generate code that’s as efficient as a Java statement and  yet lets you extract the repeated logic into a library function? Indeed, the Kotlin compiler allows you to do that.
 * -> If you mark a function with the inline modifier, the compiler won’t generate a function call when this function is used
 * > and instead will replace every call to the function with the actual code implementing the function.
 * */

//8.2.1. How inlining works
/**
 * When you declare a function as inline, its body is inlined—in other words,
 * it’s substituted directly into places where the function is called instead of being invoked normally.
 * */
//Defining an inline function
inline fun <T> synchronized(lock: Lock, action: () -> T): T {
    lock.lock()
    try {
        return action()
    }
    finally {
        lock.unlock()
    }
}

fun use1(){
//    val lock = Lock()
//    synchronized(lock){
//       //
//    }
}

fun foo(l: Lock) {
    println("Before sync")
    synchronized(l) {
        println("Action")
    }
    println("After sync")
}
//The compiled version for foo body
fun fooCompiled(lock: Lock) {
    println("Before sync")
    //inlined
    lock.lock()
    try {
        //inlined
        println("Action")
    }
    //inlined
    finally {
        lock.unlock()
    }

    println("After sync")
}

//8.2.2. Restrictions on inline functions