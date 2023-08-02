package com.ananth.kotlinplayground.coroutines.coroutine.exceptionhandling

import kotlinx.coroutines.*

fun main(){
    val scope = CoroutineScope(Job())

    val deferred = scope.async{ //here exception will not shut down the app, and it will not propagate exception to parent. so exception is encapsulated in the deferred object
        delay(200)
        throw RuntimeException()
    }
    scope.launch {
        deferred.await()
    }

    Thread.sleep(500) //so JVM does not shutdown prematurely.
}