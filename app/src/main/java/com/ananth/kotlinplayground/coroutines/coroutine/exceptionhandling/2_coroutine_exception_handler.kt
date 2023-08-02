package com.ananth.kotlinplayground.coroutines.coroutine.exceptionhandling

import com.ananth.kotlinplayground.coroutines.coroutine.structured_concurrency.scope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable in Coroutine Exception Handler")
    }
    val scope = CoroutineScope(Job() + exceptionHandler)
    scope.launch {
        launch {
            launch {
                throw RuntimeException() //if exception happened in nested child coroutine then exception would be propagated to parent job
            }
            throw RuntimeException()
        }
        throw RuntimeException()
    }

    Thread.sleep(500)
}