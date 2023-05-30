package com.ananth.kotlinplayground.lambdas

import com.ananth.kotlinplayground.R

fun main(){

}

//Java - android onClick listener

//button.setOnClickListener(new OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        ...
//    }
//}

//In Kotlin, you can pass a lambda instead:

//button.setOnClickListener { view -> ... }

//Parameters of the lambda correspond to method parameters.

//This works because the OnClickListener interface has only one abstract method. Such interfaces are called functional interfaces, or SAM interfaces,

/**
 * Kotlin allows you to use lambdas when calling Java methods that take functional interfaces as parameters,
 * ensuring that your Kotlin code remains clean and idiomatic.
 * */

//5.4.1. Passing a lambda as a parameter to a Java method

/**
 * You can pass a lambda to any Java method that expects a functional interface.
 *  For example, consider this method, which has a parameter of type Runnable:
 *
 *  ->Note that when we say “an instance of Runnable,” what we mean is “an instance of an anonymous
 *  class implementing Runnable.”
 *  The compiler will create that for you and will use the lambda as the body of the single abstract method—the run method, in this case.
 *
 *  You can achieve the same effect by creating an anonymous object that implements Runnable explicitly:
 *
 *  But there’s a difference. When you explicitly declare an object, a new instance is created on each
 *  invocation. With a lambda, the situation is different: if the lambda doesn’t access any variables from
 *  the function where it’s defined, the corresponding anonymous class instance is reused between calls:
 *  postponeComputation(1000){println(42)}
 * */
//void postponeComputation(int delay, Runnable computation);

//In Kotlin, you can invoke it and pass a lambda as an argument. The compiler will automatically convert it into an instance of Runnable:

//postponeComputation(1000) { println(42) }

//postponeComputation(1000, object : Runnable {
//    override fun run() {
//        println(42)
//    }
//})

//reuse happening

/*
val runnable = Runnable { println(42) }
fun handleComputation() {
    postponeComputation(1000, runnable)
}
*/

//new object will be created for every invoke, because lambda accessing the local parameter

fun handleComputation(id: String) {
//    postponeComputation(1000) { println(id) }
}


//5.4.2. SAM constructors: explicit conversion of lambdas to functional interfaces

/**
 * A SAM constructor is a compiler-generated function that lets you perform an explicit conversion of a
 * lambda into an instance of a functional interface.
 * lambda into an instance of a functional interface.
 * For instance, if you have a method that returns an instance of a functional interface,
 * you can’t return a lambda directly; you need to wrap it into a SAM constructor. Here’s a simple example.
 * ->The name of the SAM constructor is the same as the name of the underlying functional interface.
 *
 * ->In addition to returning values, SAM constructors are used when you need to store a functional
 * ->interface instance generated from a lambda in a variable.
 * ->Suppose you want to reuse one listener for several buttons,
 * */

//Using a SAM constructor to return a value

fun createAllDoneRunnable() : Runnable{
    return Runnable{ println("All done!") }
}

fun testSAM(){
    createAllDoneRunnable().run()
}

//Using a SAM constructor to reuse a listener instance

//val listener = OnClickListener { view ->
//    val text = when (view.id) {
//        R.id.button1 -> "First button"
//        R.id.button2 -> "Second button"
//        else -> "Unknown button"
//    }
//    toast(text)
//}
//button1.setOnClickListener(listener)
//button2.setOnClickListener(listener)