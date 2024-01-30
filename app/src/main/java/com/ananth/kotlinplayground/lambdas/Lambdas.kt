package com.ananth.kotlinplayground.lambdas

fun main(){
    handle()
    handleWithLambda {
        println("this is test")
    }
}
//Lambdas
/**
 * Lambdas are essentially small chunks of code, that can be passed to other functions.
 * With lambdas, you can easily extract common code structures into library functions.
 * and Kotlin Standard Library makes heavy uses of them.
 * one of the most common uses for lambdas is working with collections.
 * */

fun tes(){
    println("this is test")
}

fun handle(){
    tes()
    println("Handled the test")
}

//using lambda

fun handleWithLambda(block:()->Unit){
    block()
    println("Handled the test")
}