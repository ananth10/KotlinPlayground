package com.ananth.kotlinplayground

import android.util.Log
import java.util.Calendar

fun main(){

}

/**The purpose of scope function is to provide a block with the context of a given object,
 * There are five of them, let, apply, with, run, also
 * */

data class SocialUser(var name: String, var job:String, var location:String)

fun getBasicSalary(): Int{
    return  1000;
}

fun calculateHR(basicSalary : Int){
    //
}

fun calculateDA(basicSalary: Int){
    //
}

fun calculateTA(basicSalary: Int){
    //
}

/**let*/
//It can be used to invoke one, more functions on results of call chains

//without let
fun withOutLet(){
    val basicSalary = getBasicSalary();
    calculateHR(basicSalary)
    calculateDA(basicSalary)
    calculateTA(basicSalary)
}

//with let

fun withLet(){
    val basicSalary = getBasicSalary().let {
        calculateHR(it)
        calculateDA(it)
        calculateTA(it)
    }
}

/** apply*/
//Use it when we accessing multiple properties of an object

fun withoutApply(){
   val socialUser = SocialUser("Ajith", "Software engineer", "Coimbatore")
    socialUser.name = "Arun";
    socialUser.job = "Mechanic"
    socialUser.location = "Bangalore"
}

fun withApply(){

    val socialUser = SocialUser("Smith","Doctor", "Coimbatore").apply {
        name = "Vimal"
        job = "Killer"
        location = "Chennai"
    }

}

/** with*/
// Its also used to call multiple methods on same object

fun withoutWith(){
    val basicSalary = getBasicSalary();
    calculateHR(basicSalary)
    calculateDA(basicSalary)
    calculateTA(basicSalary)
}

fun withWith(){
    val basicSalary = getBasicSalary();
    with(basicSalary){
        calculateHR(this)
        calculateDA(this)
        calculateTA(this)
    }
}

/** run */
//Its useful when we want to do object initialization and some computation of return value.

fun withRun(){
    val socialUser = SocialUser("Kan","Clerk", "Pune").run {
        name = "Josh"
        //some function to do computation
    }
}

/** also */
//Use it for actions that need a reference rather to object than its property and functions or when you dont want to shadow this reference from an outer scope

fun withAlso(){

    val numbers = listOf(1,2,3,4,5,6,7,8,9)
    numbers.also {
        println(it)
    }
}

/** Apply Vs With*/
/**
 * -> apply is an extension function on a type                     Vs  with also used to change property of instances
 * -> it requires an object reference to run into an expression    Vs  we don' need object reference here. we can change property with using Dot operator.
 * -> it also returns an object reference on completion            Vs
 * with runs without an object whereas apply need one object to run
 * apply runs on the object reference, but with simply passes as argument
 * */

fun applyAndWith(){

    val user = SocialUser("smith","software","coimbatore")

    user.apply {
        this.name = "john"
    }

    with(user){
        name = "ananth"
    }
}

fun repeatEx(){
    repeat(10){
        println(it)
    }
}

//Function literals with receiver

inline fun createString(block:StringBuilder.()->Unit):String{
    val sb = StringBuilder()
    sb.block()
    return sb.toString()
}

fun exa(){
    val s = createString {
        append("2")
        append("3")
    }

    val text = "hi"

    val letResult = text?.let {
        "$it, user"
    }

    //we can use run for null check
    val runResult = text?.run{
        length // this can be omitted
    }

    // we can user run for transformation
    val date = Calendar.getInstance().run {
        set(Calendar.YEAR,2030)
        get(Calendar.DAY_OF_YEAR)
    }


    //also

    //Receiver is not used inside the block
    val num = 1234.also {
        Log.d("tag","function did its job")
    }
    //initializing an object
    var bar: Bar = Bar().also {
        it.number = "he"
    }

    //Assignment of calculated value to field

    val value = calculate().also {
        val final = it
    }



    //apply
    //Initialization of object
    val bar1 = Bar().apply {
        foo1 = "cool"
        foo2 = "nice"
    }


    //with
    //Working with an object in a confined scope
    val str = with(java.lang.StringBuilder()){
        append("hi").append("you")
        toString()
    }

}

fun calculate():Int{
    return 1+2
}
class Bar(){
    var number = ""

    var foo1= ""
    var foo2 = ""
}
