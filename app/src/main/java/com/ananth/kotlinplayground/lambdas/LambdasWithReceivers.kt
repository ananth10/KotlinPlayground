package com.ananth.kotlinplayground.lambdas

import android.app.AlertDialog
import android.content.Context
import android.widget.TextView

fun main(){

}

//Lambdas with receivers: "with" and "apply"

/**
 * with and apply functions are convenient, and we will find many uses for them without understanding how they are declared.
 * and also we can declare similar functions for our own needs
 * -> the ability to call methods of a different object in the body of a lambda without any additional qualifiers. such a lambdas called "Lambda with receivers"
 * */

//5.5.1. The “with” function
/**
 * Many languages have special statements you can use to perform multiple operations
 * on the same object without repeating its name. kotlin also has this facility,
 * but its provided as library function called "with", not as a special language construct
 * */

fun alphabet(): String {
    val result = StringBuilder()
    for (letter in 'A'..'Z') {
        result.append(letter)
    }
    result.append("Now I know alphabets")
    return result.toString()
}
/**
 * In the above example, we call several methods on the result instance and repeating,
 * the result name in each call. this is not bad, what if the expression you were using longer or repeated more often?
 * here you can re-write the code using "with"
 * */

fun alphabetWith(): String {
    val result = StringBuilder()
    return with(result) {
        for (letter in 'A'..'Z') {
            this.append(letter)
        }
        append("Now I know alphabet")
        this.toString()
    }
}
/**
 * ->The with structure looks like a special construct, but it’s a function that takes two arguments:
 * stringBuilder, in this case,and a lambda
 * ->The with function converts its first argument into a receiver of the lambda that’s passed as a second argument.
 * You can access this receiver via an explicit this reference.
 *
 * Regular function - regular lambda
 * Extension function - Lambda with receiver
 *
 * A lambda is a way to define behavior similar to a regular function.
 *  A lambda with a receiver is a way to define behavior similar to an extension function
 *  Let’s refactor the initial alphabet function even further and get rid of the extra stringBuilder variable.
 *  ->The value that with returns is the result of executing the lambda code.
 *  -> The result is the last expression in the lambda
 *  ->But sometimes you want the call to return the receiver object,not the result of executing the lambda.
 *  That’s where the apply library function can be of use.
 *
 * */

//This function now only returns an expression
fun alphabet1() = with(StringBuilder()) {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("Now I know alphabet")
    toString()
}

//5.5.2. The “apply” function

/**
 * The apply function works almost exactly the same as with;
 * ->the only difference is that apply always returns the object passed to it as an argument (in other words, the receiver object).
 * ->The apply function is declared as an extension function
 * -> Its receiver becomes the receiver of the lambda passed as an argument.
 * -> The result of executing apply is StringBuilder,so you call toString to convert it to String afterward
 *
 * -> One of many cases where this is useful is when you’re creating an instance of an object and need to initialize some properties right away.
 * -> In Java, this is usually accomplished through a separate Builder object;
 * and in Kotlin, you can use apply on any object without any special support from the library where the object is defined.
 * */
// Using apply to build the alphabet
fun alphabet2() = StringBuilder().apply{
    for(letter in 'A'..'Z'){
        append(letter)
    }
    append("Now,I know alphabets")
}.toString()

//Using apply to initialize a TextView
fun createViewWithCustomAttributes(context: Context) =
    TextView(context).apply {
        text = "Sample Text"
        textSize = 20.0F
        setPadding(10, 0, 0, 0)
    }

//Using buildString to build the alphabet, use buildString library function instead of StringBuilder

fun alphabet3() = buildString{
    for(letter in 'A'..'Z'){
        append(letter)
    }
    append("Now,I know alphabets")
    toString()
}

val test = ::alphabet2 // method reference
//Lambdas with receivers are great tools for building DSLs;
