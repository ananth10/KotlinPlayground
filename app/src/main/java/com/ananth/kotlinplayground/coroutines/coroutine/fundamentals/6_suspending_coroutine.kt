package com.ananth.kotlinplayground.coroutines.coroutine.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { suspendedCoroutine(1,500) },
        async { suspendedCoroutine(2,400) },
        async {
            repeat(5){
                println("another task is working${Thread.currentThread().name}")
                delay(100)
            }
        },
    )
    println("main ends")
}
//Coroutines are cooperative routines
//1.suspend functions that perform some long running operation(s) and can be suspended.
//2.and can only be called from other suspend function or coroutine
//3.suspend functions can be paused at Suspension points
private suspend fun suspendedCoroutine(number:Int, delay:Long){
    println("Coroutine $number starts work")
    delay(delay)
    println("Coroutine $number has finished")

}