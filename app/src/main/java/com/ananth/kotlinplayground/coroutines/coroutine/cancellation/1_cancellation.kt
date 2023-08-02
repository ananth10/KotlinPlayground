package com.ananth.kotlinplayground.coroutines.coroutine.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch {
        repeat(10) {
            println("Operation number$it")
            try {
                delay(100)
            } catch (e: CancellationException) {
                println("Cancellation exception thrown")
                throw CancellationException()
            }
        }
    }
    delay(250)
    println("Cancelling our coroutine")
    job.cancel()
}