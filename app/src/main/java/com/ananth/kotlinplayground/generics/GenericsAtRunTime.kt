package com.ananth.kotlinplayground.generics

import java.lang.IllegalArgumentException

fun main(){
    printSum(setOf(1,2,3))
    printSum(setOf("a","b"))
}

//type check

fun printSum(c:Collection<*>){
    val intList = c as? List1<Int>?:throw IllegalArgumentException("List is expected")

}