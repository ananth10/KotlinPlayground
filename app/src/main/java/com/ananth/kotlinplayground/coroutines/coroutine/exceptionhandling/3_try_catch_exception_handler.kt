package com.ananth.kotlinplayground.coroutines.coroutine.exceptionhandling

import kotlinx.coroutines.*

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Exception got $throwable")
    }

    val scope = CoroutineScope(Job())

    scope.launch(exceptionHandler) {

        launch {
            println("starting task 1")
            delay(100)
            throw RuntimeException()
        }

        launch {
            println("starting task 2")
            delay(1000)
            println("completed task 2")
        }
    }
    //try catch, it will catch exception only for first coroutine and it will never propagate exception to parent.

//    scope.launch {
//
//        launch {
//            println("starting task 1")
//            delay(100)
//            try {
//                throw RuntimeException()
//            } catch (e: Exception) {
//                println("Caught exception $e")
//            }
//        }
//
//        launch {
//            println("starting task 2")
//            delay(1000)
//            println("completed task 2")
//        }
//    }

    Thread.sleep(1500)
}