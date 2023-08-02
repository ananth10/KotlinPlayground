package com.ananth.kotlinplayground.coroutines.coroutine.structured_concurrency

import kotlinx.coroutines.*

fun main(): Unit = runBlocking {
    val scope = CoroutineScope(Dispatchers.Default)

    scope.launch {
        delay(1000)
        println("Coroutine 1 has completed")
    }.invokeOnCompletion { cause: Throwable? ->
        if(cause is CancellationException){
            println("Coroutine 1 has cancelled")
        }
    }

    scope.launch {
        delay(1000)
        println("Coroutine 1 has completed")
    }.invokeOnCompletion { cause: Throwable? ->
        if(cause is CancellationException){
            println("Coroutine 2 has cancelled")
        }
    }

    scope.cancel()
}