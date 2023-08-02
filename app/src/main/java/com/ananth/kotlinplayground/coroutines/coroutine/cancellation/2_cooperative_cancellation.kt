package com.ananth.kotlinplayground.coroutines.coroutine.cancellation

import kotlinx.coroutines.*

fun main() = runBlocking{

    val job = launch(Dispatchers.Default) {
        repeat(10){
//            yield() , we can use yield instead of ensureActive
//            ensureActive() //check current job is cancelled or not
            if(isActive) { //used isActive instead of yield because isActive does not throw cancellation exception immediately
                println("Operation number $it")
                Thread.sleep(100) //suspend function cooperative regarding cancellation
            }else{
                //do clean up operation, if needed
//                return@launch //return from the coroutine. or throw cancellation exception
                throw CancellationException()
            }
        }
    }

    delay(250)
    println("Cancelling coroutine")
    job.cancel()
}