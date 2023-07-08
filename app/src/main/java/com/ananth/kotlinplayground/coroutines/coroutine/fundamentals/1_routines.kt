package com.ananth.kotlinplayground.coroutines.coroutine.fundamentals

fun main(){
    println("main starts")
    routine(1,500)
    routine(1,300)
    println("main ends")
}

fun routine(number: Int,delay:Long){
    println("Routine $number starts work")
    Thread.sleep(delay)
    println("Routine $number finished")
}