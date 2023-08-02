package com.ananth.kotlinplayground.coroutines.coroutine.structured_concurrency

import kotlinx.coroutines.*

fun main() = runBlocking{
    val scope = CoroutineScope(Dispatchers.Default)
    val parentJob = scope.launch {
        launch {
            delay(100)
            println("Child coroutine 1 has completed")
        }
        launch {
            delay(100)
            println("Child coroutine 2 has completed")
        }
    }

    parentJob.join()//wait until all child coroutines get completed.
    println("Parent coroutine completed")
}