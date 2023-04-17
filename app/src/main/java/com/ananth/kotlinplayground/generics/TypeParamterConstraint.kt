package com.ananth.kotlinplayground.generics


import java.lang.Appendable
import java.lang.StringBuilder

/**
 * Type parameter constraints let you restricts the types that can be used as the type arguments
 * in a class or function
 * */
fun main(){
   val list = listOf(1,2,3)
   val list1 = listOf("a","b")

    list.sum() //allowed because int is subtype of Number type

//    list1.sum() //not allowed because String is not subtype of Number

    oneHalf(2)

    val helloWorld = StringBuilder("Hello World")
    ensureTrailingPeriod(helloWorld)

    process<String>() //allowed
//    process<String?>() //not allowed
}

//here we set Number type as upper bound
fun <T:Number> List<T>.sum():T {
    return this[0]
}

//ex 2

fun <T:Number> oneHalf(value:T):Double{
    return value.toDouble()
}

//ex 3

fun <T:Comparable<T>> findMax(first:T,second:T):T{
    return if(first>second) first else second
}


//ex4
//multiple type parameters with constraints
fun <T> ensureTrailingPeriod(seq:T) where T:CharSequence,T:Appendable{
   if(!seq.endsWith('.')){
       seq.append('.')
   }
}


//restrict null type

fun <T:Any>process(){
    println()
}