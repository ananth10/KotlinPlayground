package com.ananth.kotlinplayground.lambdas

import java.util.Objects

fun main(){
    test()
    test1()
}

//1. Lambdas Expressions And Member reference

//Introduction to lambdas: blocks of code as function parameters.
/**
 * -> Passing and storing pieces of behavior in our code is a frequent task.
 * ->For example, we often need to express ideas like "when an event happens. run the handler" or "Apply this operation to all elements in a data structure"
 * ->In older version of Java, we could accomplish this through anonymous inner classes, This techniques works and requires verbose code.
 *
 * ->Functional programming offers you another approach to solve this problem: ability to treat functions as values
 * ->Instead of declaring a class and passing an instance of that class to a function, we can pass a function directly. with lambdas expression.
 * ->code even more concise, we don't need to declare a function: instead, we can  effectively, pass a block of code directly as a function parameter.
 * -> lets look at an example
 * ->The notation to express just the behavior—what should be done on clicking
 * ->helps eliminate redundant code. In Kotlin, as in Java 8, you can use a lambda.
 * */

/* Java */
//button.setOnClickListener(new OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        /* actions on click */
//    }
//});

//kotlin
//button.setOnClickListener { /* actions on click */ }

//2. Lambdas and Collections
/**
 * ->One of the main tenets of good programming style is to avoid any duplication in your code.
 * ->Most of tasks we perform with collections follow a few common patterns, so that code
 * ->that implements should live in a library
 * -> But without lambdas, it’s difficult to provide a good, convenient library for working with collections.
 * ->Thus if you wrote your code in Java (prior to Java 8), you most likely have a habit of implementing everything on your own. This habit must be changed with Kotlin!
 * */
//Let’s look at an example. You’ll use the Person class that contains information about a person’s name and age.
data class Person(val name: String, val age: Int)

//Suppose you have a list of people, and you need to find the oldest of them.
//If you had no experience with lambdas, you might rush to implement the search manually.
//You’d introduce two intermediate variables—one to hold the maximum age and another to store the first found person of this age—
//and then iterate over the list, updating these variables.

fun findTheOldest(people: List<Person>) {
    var maxAge = 0
    var theOldest: Person? = null
    for (person in people) {
        if (person.age > maxAge) {
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}

fun test(){
    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    findTheOldest(people)
}

//With enough experience, you can bang out such loops pretty quickly.
// But there’s quite a lot of code here,
//and it’s easy to make mistakes. For example,
//you might get the comparison wrong and find the minimum element instead of the maximum.

//using kotlin library function
fun test1() {
    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    println(people.maxBy { it.age })
}

/**
 * ->The maxBy function can be called on any collection and takes one argument:
 * ->the function that specifies what values should be compared to find the maximum element.
 * ->The code in curly braces { it.age } is a lambda implementing that logic.
 * ->It receives a collection element as an argument (referred to using it) and returns a value to compare.
 * -> In this example, the collection element is a Person object, and the value to compare is its age, stored in the age property.
 *
 * */

//If a lambda just delegates to a function or property, it can be replaced by a member reference.
fun test2() {
    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    println(people.maxBy(Person::age))
}

//3. Syntax for lambda expression
/**
 * ->As we’ve mentioned, a lambda encodes a small piece of behavior that you can pass around as a value.
 * ->It can be declared independently and stored in a variable.
 * ->But more frequently, it’s declared directly when passed to a function.
 * ->A lambda expression in Kotlin is always surrounded by curly braces.  Note that there are no parentheses around the arguments.
 * -> The arrow separates the argument list from the body of the lambda.
 * ->You can store a lambda expression in a variable and then treat this variable like a normal function
 * */
 fun test3(){
    val sum = { x: Int, y: Int -> x + y }
    println(sum(2,3))
 }
//If you want to, you can call the lambda expression directly:
fun test4(){
    { println(42) }()
}
//If you need to enclose a piece of code in a block, you can use the library function run that executes the lambda passed to it:

fun test5(){
    run { println(42) }
}

//If you rewrite this example without using any syntax shortcuts, you get the following:
//people.maxBy({ p: Person -> p.age })

/**
 * ->Let’s make these improvements, starting with braces.
 * ->In Kotlin, a syntactic convention lets you move a lambda expression out of parentheses if it’s the last argument in a function call
 * ->example, the lambda is the only argument, so it can be placed after the parentheses:
 * ->people.maxBy() { p: Person -> p.age }
 * ->When the lambda is the only argument to a function, you can also remove the empty parentheses from the call:
 * ->people.maxBy{ p: Person -> p.age }
 * ->people.maxBy{p->p.age}
 * ->people.maxBy{it.age} - last simplification, This default name is generated only if you don’t specify the argument name explicitly.
 *
 * */
//Note
/**
 * The it convention is great for shortening your code,
 * but you shouldn’t abuse it. In particular, in the case of nested lambdas,
 * it’s better to declare the parameter of each lambda explicitly;
 * otherwise it’s difficult to understand which value the it refers to.
 * It’s useful also to declare parameters explicitly if the meaning or the type of the parameter isn’t clear from the context.
 *
 * -> If you store a lambda in a variable,  there’s no context from which to infer the parameter types,
 * so you have to specify them explicitly:
 * val getAge = { p: Person -> p.age }
 * -> lambdas aren’t constrained to such a small size and can contain multiple statements.
 * the last expression is the result:
 *
>>> val sum = { x: Int, y: Int ->
...    println("Computing the sum of $x and $y...")
...    x + y
... }
>>> println(sum(1, 2))
 * */
