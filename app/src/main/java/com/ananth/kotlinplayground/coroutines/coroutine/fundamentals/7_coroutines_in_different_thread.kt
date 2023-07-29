package com.ananth.kotlinplayground.coroutines.coroutine.fundamentals

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { suspendedCoroutine(1,500) },
        async { suspendedCoroutine(2,400) },
    )
    println("main ends")
}
//Coroutines are cooperative routines
//1.suspend functions that perform some long running operation(s) and can be suspended.
//2.and can only be called from other suspend function or coroutine
//3.suspend functions can be paused at Suspension points
private suspend fun suspendedCoroutine(number:Int, delay:Long){
    println("Coroutine $number starts work ${Thread.currentThread().name}")
    delay(delay)
    withContext(Dispatchers.Default){
        println("Coroutine $number has finished on ${Thread.currentThread().name}")
    }

}