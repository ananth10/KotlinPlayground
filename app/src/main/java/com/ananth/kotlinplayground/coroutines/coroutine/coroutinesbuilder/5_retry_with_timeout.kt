package com.ananth.kotlinplayground.coroutines.coroutine.coroutinesbuilder

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val job = launch(start = CoroutineStart.LAZY) {
            delay(400)
            println("Hi")
        }
        delay(200)
        job.start()
        job.join()
        println("End of run blocking")
    }
}

//withTimeout with exception
suspend fun networkRequest5(): String {
    try {
        retryWithTimeout(2,1000) {  //this code block should complete within given timeframe.
            delay(500) //fetching some data from backend
        }
    } catch (e: TimeoutCancellationException) {
        println("Error: ${e.message}")
    }
    return "welcome"
}

private suspend fun retryWithTimeout(
    numberOfRetries:Int,
    timeout:Long,
    block: suspend ()->Unit
)= retry(
    numberOfRetries,
){
    withTimeout(timeout){
        block()
    }
}


private suspend fun <T> retry(numberOfRetries: Int, block: suspend () -> T): T {
    try {
        repeat(numberOfRetries) {
            return block()
        }
    } catch (e: Exception) {
    }
    return block()
}