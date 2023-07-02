package com.ananth.kotlinplayground.coroutines.flow.basics.hot_and_cold_flows

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

suspend fun main():Unit = coroutineScope {
  val job = launch {
       coldFlow()
           .onCompletion {
               println("Flow of Collector 1 completed")
           }//1. cold flow becomes active on collection
           .collect{
               println("Collector 1 collects $it")
           }

   }
    delay(1500)
    job.cancelAndJoin() //2. become inactive on cancellation of the collecting coroutine

    //3. emit individual emissions to every collector
    launch {
        coldFlow()
            .onCompletion {
                println("Flow of Collector 1 completed")
            }
            .collect{
                println("Collector 1 collects $it")
            }
    }
}

//1. cold flow becomes active on collection
//2. become inactive on cancellation of the collecting coroutine
//3. emit individual emissions to every collector
fun coldFlow() = flow{
   println("Emitting 1")
    emit(1)

    delay(1000)
    println("Emitting 2")
    emit(2)

    delay(1000)
    println("Emitting 3")
    emit(3)
}