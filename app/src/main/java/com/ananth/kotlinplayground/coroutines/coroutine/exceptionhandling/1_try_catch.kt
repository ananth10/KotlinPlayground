package com.ananth.kotlinplayground.coroutines.coroutine.exceptionhandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {
    val job = CoroutineScope(Job())
    //when some code throw exception
       job.launch {
           try {
               launch {//here, exception will not be propagated to parent coroutine and also it will crash the app
                   functionThatThrowsException()
               }
           } catch (e: Exception) {
               println("Exception: $e")
           }
       }
    Thread.sleep(500)
}

private fun functionThatThrowsException() {
    throw RuntimeException()
}