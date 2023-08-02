package com.ananth.kotlinplayground.coroutines.coroutine.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {

    try {
        coroutineScope {
            launch {
                throw RuntimeException()
            }
        }
    } catch (e: Exception) {
        println("Caught $e")
    }
}