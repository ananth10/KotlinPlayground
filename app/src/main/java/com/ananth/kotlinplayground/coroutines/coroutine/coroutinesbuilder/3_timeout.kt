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
suspend fun networkRequest1(): String {
    try {
        withTimeout(2000) {  //this code block should complete within given timeframe.
            delay(500) //fetching some data from backend
        }
    } catch (e: TimeoutCancellationException) {
        println("Error: ${e.message}")
    }
    return "welcome"
}

//withTimeout with null value

suspend fun networkRequest2(): String {
        val result = withTimeoutOrNull(2000) {  //this code block should complete within given timeframe.
            delay(500) //fetching some data from backend
        }
       if(result!=null)
          "ok"
    return "welcome"
}