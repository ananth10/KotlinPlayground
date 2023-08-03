package com.ananth.kotlinplayground.higher_order_function

import com.ananth.kotlinplayground.OS
import com.ananth.kotlinplayground.performRequest
import java.util.concurrent.locks.Lock

fun main(){
//    testPerformRequest()
//    filterUse()
    lookForAlice11(listOf(Person("john",23), Person("Alice",23), Person("Ken",40)))
    lookForAlice12(listOf(Person("john",23), Person("Alice",23), Person("Ken",40)))
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

/**
 * ->Generally, the parameter can be inlined if it’s called directly or passed as an argument to another inline function.
 * ->Otherwise, the compiler will prohibit the inlining of the parameter with an error message that says “Illegal usage of inline-parameter.”
 * ->For example, various functions that work on sequences return instances of classes that represent the corresponding sequence operation and receive the lambda as a constructor parameter.
 * */

//fun <T, R> Sequence<T>.map(transform: (T) -> R): Sequence<R> {
//    return TransformingSequence(this, transform)
//}
/**
 * ->The map function doesn’t call the function passed as the transform parameter directly.
 * ->Instead, it passes this function to the constructor of a class that stores it in a property.
 * ->To support that, the lambda passed as the transform argument needs to be compiled into the standard non-inline representation,
 * as an anonymous class implementing a function interface.
 *
 * ->If you have a function that expects two or more lambdas as arguments, you may choose to inline only some of them.
 * ->This makes sense when one of the lambdas is expected to contain a lot of code or is used in a way that doesn’t allow inlining.
 * ->You can mark the parameters that accept such non-inlineable lambdas with the noinline modifier:
 *
 * ->Note that the compiler fully supports inlining functions across modules, or functions defined in third-party libraries.
 * ->You can also call most inline functions from Java; such calls will not be inlined, but will be compiled as regular function calls.
 * */

inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) {
    // ...
}

//8.2.3. Inlining collection operations
/**
 * ->Most of the collection functions in the standard library take lambda expressions as arguments.
 * ->Would it be more efficient to implement these operations directly, instead of using the standard library functions?
 *
 * */
//Filtering a collection using a lambda
data class Person(val name: String, val age: Int)
val people = listOf(Person("Alice", 29), Person("Bob", 31))

fun filterPeople(){
    println(people.filter { it.age>29 })
}

//Filtering a collection without using a lambda
fun filterPeople1(){
    val result = mutableListOf<Person>()
    for (people in people){
        if(people.age>29){
            result.add(people)
        }
    }
    println("Result: $result")
}

/**
 * -> in kotlin filter function declared as inline. in means the bytecode of the filter function, together with the bytecode of the lambda passed to it. will be inlined where the filter is called.
 * ->the bytecode generated for the first version that uses filter is roughly the same as the bytecode generated for the second version
 * ->Kotlin’s support for inline functions ensures that you don’t need to worry about performance.
 * */

//8.3. Control flow in higher-order functions
/**
 * ->When you start using lambdas to replace imperative code constructs such as loops,
 * you quickly run into the issue of return expressions.
 * ->Putting a return statement in the middle of a loop is a no-brainer
 * But what if you convert the loop into the use of a function such as filter? How does return work in that case?
 *
 * */

//8.3.1. Return statements in lambdas: return from an enclosing function
//Listing 8.18. Using return in a regular loop
fun lookForAlice(people: List<Person>) {
    for (person in people) {
        if (person.name == "Alice") {
            println("Found!")
            return
        }
    }
    println("Alice is not found")
}
//Listing 8.19. Using return in a lambda passed to forEach
/**
 * -> If you use the return keyword in a lambda, it returns from the function in which you called the lambda, not just from the lambda itself. Such a return statement is called a non-local return,
 * because it returns from a larger block than the block containing the return statement.
 * -> To understand the logic behind the rule, think about using a return keyword in a for loop or a synchronized block in a Java method.
 * ->It’s obvious that it returns from the function and not from the loop or block.
 * ->Kotlin allows you to preserve the same behavior when you switch from language features to functions that take lambdas as arguments.
 *
 * ->Note that the return from the outer function is possible only if the function that takes the lambda as an argument is inlined.
 * ->the body of the forEach function is inlined together with the body of the lambda,
 * so it’s easy to compile the return expression so that it returns from the enclosing function
 * -> Using the return expression in lambdas passed to non-inline functions isn’t allowed.
 * ->A non-inline function can save the lambda passed to it in a variable and execute it later, when the function has already returned,
 * */
fun lookForAlice1(people: List<Person>) {
    people.forEach {
        if (it.name == "Alice") {
            println("Found!")
            return
        }
    }
    println("Alice is not found")
}

//8.3.2. Returning from lambdas: return with a label
/**
 * ->You can write a local return from a lambda expression as well. A local return in a lambda is similar to a break expression in a for loop.
 * ->It stops the execution of the lambda and continues execution of the code from which the lambda was invoked.
 * -> To distinguish a local return from a non-local one, you use labels
 * ->  You can label a lambda expression from which you want to return,  and then refer to this label after the return keyword.
 *
 * ->Alternatively, the name of the function that takes this lambda as an argument can be used as a label.
 * */

private fun lookForAlice11(people:List<Person>){
    people.forEach label@ { person ->
        if(person.name=="Alice")
            return@label
    }

    //Alternatively, the name of the function that takes this lambda as an argument can be used as a label.
    people.forEach { person ->
        if(person.name=="Alice")
            return@forEach
    }
    println("Alice might be somewhere")
}

//lambda with receiver with label

fun lambdaLabel(){
    print(StringBuilder().apply sb@{
        listOf(1,2,3).apply {
            this@sb.append(this.toString())
        }
    })
}

///8.3.3. Anonymous functions: local returns by default///
/**
 * ->An anonymous function is a different way to write a block of code passed to a function. Let’s start with an example.
 *
 * ->You can see that an anonymous function looks similar to a regular function, except that its name and parameter types are omitted.
 * ->Anonymous functions follow the same rules as regular functions for specifying the return type.
 * ->Inside an anonymous function, a return expression without a label returns from the anonymous function, not from the enclosing one.
 * ->The rule is simple: return returns from the closest function declared using the fun keyword.
 * ->Lambda expressions don’t use the fun keyword, so a return in a lambda returns from the outer function
 * */
// Using return in an anonymous function
fun lookForAlice12(people: List<Person>) {
    people.forEach(fun (person) {
        if (person.name == "Alice") return
        println("${person.name} is not Alice")
    })
}

// Using an anonymous function with filter

fun lookForAlice13(people: List<Person>) {
    people.filter(fun (person): Boolean {
        return person.age < 30
    })
}

//Using an anonymous function with an expression body
//people.filter(fun (person) = person.age < 30)


//Summary//
/**
 * ->Function types allow you to declare a variable, parameter, or function return value that holds a reference to a function.
 *
 * ->Higher-order functions take other functions as arguments or return them.
 *You can create such functions by using a function type as the type of a function parameter or return value.
 *
 * ->When an inline function is compiled, its bytecode along with the bytecode of a lambda passed to it is inserted directly into the code of the calling function,
 * which ensures that the call happens with no overhead compared to similar code written directly.
 *
 * ->Higher-order functions facilitate code reuse within the parts of a single component and let you build powerful generic libraries.
 *
 * ->Inline functions allow you to use non-local returns—return expressions placed in a lambda that return from the enclosing function.
 *
 * ->Anonymous functions provide an alternative syntax to lambda expressions with different rules for resolving the return expressions
 *  You can use them if you need to write a block of code with multiple exit points
 * */