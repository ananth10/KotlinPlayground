package com.ananth.kotlinplayground.coroutines.coroutine.coroutinesbuilder

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val job = launch(start = CoroutineStart.LAZY) {
            val numOfRetry = 2;
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
suspend fun networkRequest4(): String {
    try {
        retry(2) {
            delay(500) //network call
        }
    } catch (e: TimeoutCancellationException) {
        println("Error: ${e.message}")
    }
    return "welcome"
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

//retry with some delay before calling api
private suspend fun <T> retryWithDelay(
    numberOfRetries: Int,
    initialDelayInMillis: Long = 100,
    maxDelayMillis: Long = 1000,
    factor: Double,
    block: suspend () -> T
): T {
    var currentDelay = initialDelayInMillis;
    try {
        repeat(numberOfRetries) {
            return block()
        }
    } catch (e: Exception) {
    }
    delay(currentDelay)
    currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
    return block()
}