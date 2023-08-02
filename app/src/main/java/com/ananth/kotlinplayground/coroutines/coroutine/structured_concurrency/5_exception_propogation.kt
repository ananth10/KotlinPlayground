package com.ananth.kotlinplayground.coroutines.coroutine.structured_concurrency

import kotlinx.coroutines.*

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Got an exception $throwable")
    }
    val scope = CoroutineScope(Job() + exceptionHandler)
    val scope1 = CoroutineScope(SupervisorJob() + exceptionHandler) //it does not cancel child coroutine and scope is not cancelled
    scope1.launch {
        println("Coroutine 1 start")
        delay(50)
        println("Coroutine 1 fails")
        throw RuntimeException()
    }

    scope1.launch {
        println("Coroutine 2 start")
        delay(500)
        println("Coroutine 2 Completed")
    }.invokeOnCompletion { cause: Throwable? ->
        if (cause is CancellationException) {
            println("Coroutine 2 cancelled")
        }
    }

    Thread.sleep(1000)
}