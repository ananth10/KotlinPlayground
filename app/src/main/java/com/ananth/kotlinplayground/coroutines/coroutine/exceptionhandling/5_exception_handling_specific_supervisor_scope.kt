package com.ananth.kotlinplayground.coroutines.coroutine.exceptionhandling

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main() = runBlocking<Unit> {

    try {
        supervisorScope {
            launch {
                throw RuntimeException()
            }
//            throw RuntimeException()
            async {
                throw RuntimeException() //exception encapsulated in the deferred object
            }.await()//exception will be propagated to supervisor scope
        }
    } catch (e: Exception) {
        println("Caught $e")
    }
}


