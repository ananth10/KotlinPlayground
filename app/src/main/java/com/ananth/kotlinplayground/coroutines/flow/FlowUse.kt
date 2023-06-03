package com.ananth.kotlinplayground.coroutines.flow

import com.ananth.kotlinplayground.coroutines.flow.flow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

//Flow is a way of asynchronously emitting stream of sequence of data over time

//create flow

val flow = object : Flow<String> {

    override suspend fun collect(collector: FlowCollector<String>) {
        collector.emit("hi")
        collector.emit("bye")
    }

}
//used lambda syntax
//val flow1 = Flow<String> {collector ->
//    collector.emit("hi")
//    collector.emit("bye")
//}

//collect flow
suspend fun collectFlow() {
    flow.collect(object : FlowCollector<String> {
        override suspend fun emit(value: String) {
            TODO("Not yet implemented")
        }

    })
}

//used lambda syntax
suspend fun collectFlow1() {
    flow.collect { value ->
        println(value)
    }
}

//UpStreams and DownStreams
/**
 * In the context of coroutine Flow. the term upstream refers to the source of data items.
 * that are emitted in hte flow.
 * the downstream receiver, on the other hand, is the consumer of these data items that are collected fro the upstreams source.
 * Its important to note that the downstream receiver can also be a flow itself.
 * creating a chain of  data processing stages where each stage collects data from the previous stage.
 * */

//coroutine flow provides several builders for creating Flows
//Flow Builders
/**
 * 1. flowOf
 * 2. asFlow
 * 3. flow
 * */

//1. flowOf
/**
 * The flowOf function is used to create a Flow that emits a fixed set of values
 * e.g
 * */
val flow1 = flowOf("hi", "hello")

//2. asFlow
/**
 * The asFlow() function is used to convert any iterable or sequence to a Flow.
 * */
val flow2 = listOf(1, 2, 3, 4).asFlow()

//3. flow
/**
 * The flow {} builder function is used to create a custom Flow. It takes a suspending lambda that provides
 * a FlowCollector as parameter, and emits values to it using the emit() function. Here an example.
 * */

val flow3 = flow {
    for (i in 1..10) {
        delay(1000)
        emit(i)
    }
}

