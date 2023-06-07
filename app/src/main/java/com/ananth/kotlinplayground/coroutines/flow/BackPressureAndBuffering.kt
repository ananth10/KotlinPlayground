package com.ananth.kotlinplayground.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main(){
    collectSlowFlow()
    collectSlowFlowWithBuffer()
}

//Backpressure
/**
 * When dealing with large number of data, backpressure is an important concern.
 * It refers to the ability of a consumer to signal a producer to slow down the rate of production when consumer is unable to keep up with the rate of consumption.
 * In Kotlin, Flows, Backpressure is handled through suspending the producer when the downstream is not ready to receive data
 *
 * Suppose, you have flows that emits large number of items.
 * and each item requires some time-consuming processing before it can be collected.
 * the collector will wait for each item to be processed before requesting the next one.
 * This can result in a significant delay in the overall execution time.
 * */

fun slowFlow(): Flow<Int> = flow{
   repeat(10){
       delay(100)
       emit(it)
   }
}

fun collectSlowFlow(){
    runBlocking {
        measureTimeMillis {
            slowFlow().collect{
                delay(100)
                println("Received $it")
            }
            println("Done")
        }.let{
            println("Collected in $it mills")
        }

    }
}

//Buffer
/**
 * However, if we add a buffer to the flow, we can reduce the total time taken
 * to complete the collection
 * ->The buffer function receives 2 parameters; capacity and onBufferOverflow.
 * ->The capacity parameter specifies the maximum number of values that can be stored in the buffer.
 * If more values are emitted than the buffer can hold,
 * the onBufferOverflow parameter specifies what action should be taken;
 * ->SUSPEND is the default value, and it means that if the buffer is full,
 * ny attempt to send a new element to the buffer will suspend the sender until space becomes available in the buffer.
 * ->DROP_OLDEST means that if the buffer is full, any attempt to send a new
 * element to the buffer will drop the oldest element currently in the buffer and add the new element.
 * ->DROP_LATEST means that if the buffer is full, any attempt to send a new element to the buffer will drop the new element
 * and keep the current buffer contents unchanged.
 * */

fun slowFlowWithBuffer(): Flow<Int> = flow{
    repeat(10){
        delay(100)
        emit(it)
    }
}.buffer()

fun collectSlowFlowWithBuffer(){
    runBlocking {
        measureTimeMillis {
            slowFlowWithBuffer().collect{
                delay(100)
                println("Received $it")
            }
            println("Done")
        }.let{
            println("Collected in $it mills")
        }

    }
}