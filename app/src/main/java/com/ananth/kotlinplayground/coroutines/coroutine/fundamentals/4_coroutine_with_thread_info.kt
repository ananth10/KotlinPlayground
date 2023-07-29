package com.ananth.kotlinplayground.coroutines.coroutine.fundamentals

import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun main() = runBlocking {
    println("main starts")
    threadCoroutine(1,500)
    threadCoroutine(2,300)
    Thread.sleep(1000)
    println("main ends")
}

private fun threadCoroutine(number:Int, delay:Long){
        println("Coroutine $number starts work on ${Thread.currentThread().name}")
        Thread.sleep(delay)
        println("Coroutine $number has finished on ${Thread.currentThread().name}")
}