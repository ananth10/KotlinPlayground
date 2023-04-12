 package com.ananth

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.ananth.kotlinplayground.joinToString
import java.lang.StringBuilder

 val people1 = listOf(Person0("Alice",20), Person0("bob",30))
fun main(){


    findTheOldest(people1)
    findTheOldestUsingLambdas()
    sampleLambda()
    passLambdasAsNamedArgument()

    val errors = listOf("403 Forbidden", "404 Not Found", "500 server error")
    printMessageWithPrefix(errors,"Error:")
    printProblemCounts(errors)
    getSalute()
    filterSample()
    mapSample()
    mapSample1()
    testOtherFunc()
    testGroupBy()
    testFlatMap()
    testMapFilterSample()
    testSequence()
    testSequenceOrder()
    testGenerateSequence()
}

/**
 * Introduction to lambdas: blocks of code as function parameters
 * */

//Implementing a listener with an anonymous inner class
//in Java

fun listenerWithOutLambdas(){
    val button:Button?=null
    button?.setOnClickListener( View.OnClickListener {  //new keyword is omitted cause of this kotlin
        //action on onClick
    })
}

//Implementing a listener with lambdas

fun listenerWithLambdas(){
    val button:Button?=null
    button?.setOnClickListener {
        //action on onClick
    }
}

/** Lambdas and Collections*/

data class Person0(val name:String, val age:Int)

//Searching through collection manually

fun findTheOldest(people:List<Person0>){
    var maxAge=0
    var theOldest: Person0?=null
    for (person in people){
        if(person.age>maxAge){
            maxAge=person.age
            theOldest=person
        }
    }
    println(theOldest)
}

//Searching through a collection using a lambdas

fun findTheOldestUsingLambdas(){
    println(people1.maxBy { it.age })

    //Search using member reference

//    people1.maxBy { Person0::age }
}

/** Syntax for lambdas expression*/

// a lambda encodes a small piece of behavior that you can pass around as a value.
// It can be declared independently  and stored in a variable. but more frequently , its declared directly when passed to function

// {x: Int, y: Int -> x+y} - lambda expression syntax

fun sampleLambda(){
    var sum = {x:Int, y:Int ->x+y}
    println(sum(3,4))

    run { println(45) } //run execute lambda pass to it

    people1.maxBy({ p: Person0 ->p.age}) //without shortcut syntax

    people1.maxBy() { p: Person0 ->p.age } // put lambdas outside of parenthesis , if lambdas is last argument to a function

    people1.maxBy { p: Person0 ->p.age } //remove parenthesis, if lambdas is the only argument

    people1.maxBy { p->p.age } //omitting lambda parameter type

    val getAge = {p: Person0 -> p.age} // if you store lambda in a variable, there is no context from which to infer the parameter types , so you have to specify them explicitly
    people1.maxBy(getAge)

    val sum1 = {x:Int, y:Int ->
        println("Computing the sum of $x and $y...")
        x+y
    }

    println(sum1(1,2))

    people1.maxBy(Person0::age) // member reference
}

//Passing a lambda as a named argument

fun passLambdasAsNamedArgument(){
    val people = listOf(Person0("Alice",34), Person0("Bob",40))
    val names = people.joinToString(separator = ", ", transform = {p: Person0 ->p.name})
    println(names)

    //passing lambda outside of parentheses

    val names1 = people.joinToString(", "){p: Person0 ->p.name}
    println(names1)
}

/** Accessing variables in scope*/
//forEach function is one of most basic collection-manipulation functions. all it does is call the given lambda on every element in the collection

//Using function parameters in a lambda //In kotlin you are not restricted to access variables

fun printMessageWithPrefix(messages: Collection<String>, prefix:String){
    messages.forEach {
        println("$prefix $it")  //access prefix parameter in lambda
    }
}


// Changing the local variables from a lambda

fun  printProblemCounts(responses: Collection<String>){

    var clientVariables = 0
    var serverVariables = 0

    responses.forEach {
        if(it.startsWith("4")){
            clientVariables++                  //Modifying variable in lambda
        }else if (it.startsWith("5")){
            serverVariables++
        }
    }

    println("$clientVariables client errors, $serverVariables server errors")
}

/** member reference*/

//What if the code that you need to pass as a parameter is already defined as a function? of course, you can pass a lambda that calls that function. but doing so somewhat redundant.
//in Kotlin or Java 8, you can do so if you convert the function to a value You use the :: operator for that
// val getAge = Person::age / this expression is called member reference
// it provides a short syntax for creating a function value that calls exactly one method  or access a property.
// double colon separates the name of class from the name of the member you need to reference(a method or property)

// people1.maxBy(Person0::age)

fun salute() = println("Salute!")

fun getSalute(){
    run (::salute)  // reference to the top level function
}

//Its convenient to provide a member reference instead of  a lambda that delegates to a function taking several parameters

fun memberSample(){

    val action = { person: Person0, message:String ->
        sendEmail(person,message)
    }

    val nextAction = ::sendEmail   //member reference instead of lambdas
}

fun sendEmail(person0: Person0, message:String){

}

//we can store or postpone the action of creating an instances of a class using a "constructor reference".
//The constructor reference is formed by specifying the class name after the double colons

data class Person10(val name:String, val age:Int)

fun exampleConstructorRef(){
    val createPerson = ::Person10   //an action of creating an instance of "Person" is saved as a value
}

fun Person10.isAdult() = age>=20


/** Functional APIs for collections*/

//Essentials: Filter and map
//-> filter and map  functions form the basis for manipulating collections.
//filter function goes through a collection and selects the elements for which the given lambda returns true.
fun filterSample(){
    val list = listOf(1,2,3,4)
    println(list.filter { it%2==0 }) // Only even numbers remain
}

//map function applies the given function to each element in the collection and collects the results into new collection
//e.g you can transform list of numbers into a list of their suqares

fun mapSample(){
    val list = listOf(1,2,3,4,5,6)
    println(list.map { it*it })
}

fun mapSample1(){
    val people = listOf(Person10("Alice",32), Person10("Bob",20))

    //If you want to print list names , not a list of person, you can transform the list using map

    println(people.map { it.name })

    //This example can be nicely rewritten using member reference

    println(people.map { Person10::name })

    //you can chain several calls like that.

    //e.g print names of people older than 30

    println(people.filter { it.age>30 }.map { it.name })

    //find oldest people in the group and return everyone who is that age.

    val pp= people.filter { it.age == people.maxBy { person10 -> person10.age }?.age ?: 0 }

    // but above function will search 100 times if there are 100 people in the list

    //improved search
    val maxAge = people.maxBy { person10 ->person10.age}?.age
    val oldPeople = people.filter { it.age == maxAge }

    //we can also apply the filter and transformation function to maps

    val numbers = mapOf(0 to "zero",1 to "one")
    println(numbers.mapValues { it.value.toUpperCase() })

}

/** "all", "any", "count", and "find" : applying to predicate to a collection */
// Another common task is checking whether all elements in a collection match a certain condition

fun testOtherFunc(){

    val canBeInClub27 = {p: Person10 -> p.age <=27}

    val people = listOf(Person10("Alice", 27), Person10("Bob",30), Person10("John",25))
    println(people.all(canBeInClub27)) // all elements in collection should satisfy this predicate then only it will return true

    println(people.any(canBeInClub27)) // at least one element should be match

    println(people.count(canBeInClub27)) //to know how many elements satisfy condition

    println(people.find(canBeInClub27))  //to find element that satisfy the condition.
}

/** groupBy: Converting a list to a map of groups*/
// Imagine that you need to divide all elements into different groups according to some quality. For example, you want to group people of the same age. Its convenient to pass this quality directly as a parameter.

fun testGroupBy(){
    val list = listOf(Person10("Alice",30), Person10("John",20), Person10("Bob",30), Person10("Smith",25))
    println(list.groupBy { it.age })

    //we can do further modification with this group, using functions such as mapKeys and mapValues
    //Another example how to group strings by their first character using member reference.

    val list1 = listOf("a","ab","b")
//    println(list1.groupBy { String::first})
}

/** flatMap and flatten: processing elements in nested collections*/

class Book(val title:String, val authors: List<String>)

//Each book was written by one or more authors, You can compute the set of all the authors in your library\
// The flatMap function does two things : At first it transform each element to a collection according to the function given as an argument and then it combines(or flatten) several lists into one.

fun testFlatMap(){
    val list = listOf(Book("Book1", listOf("John","Smith","Ken")), Book("Book2", listOf("Smith","Ken")))
    println(list.flatMap { it.authors }.toSet())

    val strings = listOf("abc","def")
    println(strings.flatMap { it.toList() }) //toList function on string converts it into a list of characters
    //Dont need to transform anything and just need to flatten such a collection, you can use the flatten function listOfLists.flatten()
}



/** Lazy collection operations: sequences*/
// map and filter create intermediate collection and sequences give you an alternative way to perform such computation that avoids the creation of intermediate temporary objects.
//each element in a collection process one by one e.g map process first element and filter process first element and map goes to second element

fun testMapFilterSample(){
    val list = listOf(Person10("Alice",30), Person10("Bob",20), Person10("Ananth",40))
    val result = list.map (Person10::name).filter { it.startsWith("A") } // this will create 2 intermediate list one for filter and another one for map
    println(result)
}

fun testSequence(){
    val list = listOf(Person10("Alice",30), Person10("Bob",20), Person10("Ananth",40))
    val result = list.asSequence().map(Person10::name).filter { it.startsWith("A") }.toList()
    println(result)
}

 fun testSequenceOrder(){
     val list = listOf(
         Person10("Alice",29), Person10("Bob",31), Person10("Charles",31),
         Person10("Dan",21)
     )
     println(list.asSequence().map(Person10::name).filter { it.length<4}.toList())
     println(list.asSequence().filter { it.name.length<4}.map(Person10::name).toList())  //applying filter helps to reduce total number of transformation
 }

 /** Creating sequence*/

 //generateSequence function calculates the next element in a sequence given the previous one.
 ///Generating and using a sequence of natural numbers

 fun testGenerateSequence(){
     val naturalNumbers = generateSequence(0) { it+1 }
     val numbersTo100 = naturalNumbers.takeWhile { it<=100 }
     println(numbersTo100.sum())   //actual numbers in those sequence will not be evaluated until you call the terminal operation(sum in thi case)
 }

 /**Using Java functional interfaces*/
 //The good news is that Kotlin lambdas are fully interoperable with Java APIs

 // button.setOnClickListener { action herer} -> passes lambda as an argument
 //In Java (prior to Java 8), you have to create a new instance of an anonymous class to pass it as an argument to the setOnClickListener method.
 //e.g

/* button.setOnClickListener(new OnClickListener())
 {
     @override
     public void onClick(View V){

     }
 }*/

 //In Kotlin you can pass lambda instead.

/* button.setOnClickListener{ view ->...}*/ //The lambda used to implement OnClickListener has one parameter of type View, as in the onClick method.

 /**Passing a lambda as a parameter to a Java method*/
 //In Java
/*void postponeComputation(int delay, Runnable computation)*/
//In Kotlin
/*void postponeComputation(1000){println(43)}*/
//You can achieve the same effect by creating an anonymous object that implements Runnable explicitly

/*
fun handleComputation(){
     postponeComputation(1000, object : Runnable {
         override fun run() {
            println(43)
         }

     })
 }*/

 /** Lambda with receivers*/
 //def : ability to call methods of different objects in the body of lambda without any additional parameters is called lambda with receiver. e.g "with", "apply"
 //The "with" function
 // Many languages have special statements you can use to perform multiple operations on the same object without repeating its name. kotlin also has this facility, but its provided as a library function called "with",
 //not special language construct

 // building alphabets

 fun alphabet() : String {
     val result = StringBuilder()
     for(letter in 'A'..'Z'){
         result.append(letter)
     }
     result.append("\n Now I know the alphabet")
     return result.toString()
 }

 //in above example, you call several different methods on the result instance and repeating the result name in each call. but this is not bad, but what if the expression you were using was longer or repeated more often.
 //Using with to build alphabet

 fun withAlphabet():String{
     val stringBuilder = StringBuilder()
     return with(stringBuilder){// Specifies the receiver value on which you calling the methods
         for(letter in 'A'..'Z'){
             this.append(letter) //Calls a method on the receiver value though an explicit "this"
         }
         append("/n Now I know the alphabet!") //Calls a method omitting "this"
         this.toString()//returns value from lambda
     }
 }

 //The with structure looks like a special construct, but its a function that takes two arguments: stringBuilder in above case, and a lambda.
 //The convention of putting the lambda outside of the parentheses works here.
 //The with function converts its first argument into a receiver of the lambda thats passed as a second argument.

 //Using with and expression body to build the alphabet

 fun alphabet1() = with(StringBuilder()){
     for(letter in 'A'..'Z'){
         append(letter)
     }
     append("\n Now I know the alphabet")
     toString()
 }

 /**The "apply" function*/
 //The apply function works almost exactly the same as with, the only difference is that apply always returns the object passed to it as an argument(in other words, the receiver object).
 //Using apply to build the alphabet
 fun alphabetUsingApply() = StringBuilder().apply {
     for(letter in 'A'..'Z'){
         append(letter)
     }
     append("\nNow I know the alphabet")
 }.toString()

 //One of many cases where this is useful is when you are creating an instance of an object and need to initialize some properties right away.

 //Using apply to initialize a TextView
 fun createViewWithCustomAttribute(context:Context) = TextView(context).apply {
     text = "Sample Text"
     textSize = 20.0f
     setPadding(10,0,0,0)
 }

 //The apply function allows you to use the compact expression body style for the function. you create a new TextView instance and immediately pass it to apply.

 //Using buildString to build the alphabet.

 fun alphabetUsingBuildString() = buildString {
     for(letter in 'A'..'Z'){
         append(letter)
     }
     append("\n I know alphabet now")
 }

 /**Summary*/

 //Lambdas allow you to pass junk of code to the function.
 //Kotlin lets you pass lambdas to functions outside of parentheses and refer to a single parameter as "it".
 //Code in a lambda can access and modify variables in the function containing the call to the lambda.
 //You can create references to methods, constructors, and properties by prefixing the name of the function with ::,  and pass such reference to functions instead of lambdas.
 //Most common operations with collections can be performed without manually iterating over elements, using functions such as filter, map, all, any, and so on.
 //Sequences allows you to combine multiple operations on a collection without creating collections to hold intermediate results.
 //You can pass lambdas as arguments to methods that take a Java functional interface(an interface with a single abstract method, also knows as SAM interface)
 //Lambdas with receivers are lambdas in which you can directly call methods on special receiver object.
 //The with standard library function allows you to call multiple methods on the same object without repeating the reference to the object. apply lets you construct and initialize any object using a builder-style API