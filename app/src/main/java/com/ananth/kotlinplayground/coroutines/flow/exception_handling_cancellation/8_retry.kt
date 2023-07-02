package com.ananth.kotlinplayground.coroutines.flow.exception_handling_cancellation

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {

    launch {
        val stockFlow = stocksFlow()

        stockFlow
            .catch { throwable ->
                println("Handle exception in catch operator $throwable")
            }
            .collect { stock ->
                println("Collected $stock")
            }
    }
}

private fun stocksFlow(): Flow<String> = flow {
    repeat(5) { index ->
        delay(100)
        if (index < 4) {
            emit("New stock data")
        } else {
            throw NetworkException("Network Request Failed!")
        }
    }
}.retry(retries = 3) { cause ->
    println("Entered in retry with $cause")
    val shouldRetry = cause is NetworkException
    if (shouldRetry) {
        delay(1000)
    }
    shouldRetry
}.retryWhen { cause, attempt ->
    delay(1000 * attempt * 1)
    cause is NetworkException
}

class NetworkException(message: String) : Exception(message)