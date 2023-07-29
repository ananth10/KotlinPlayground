package com.ananth.kotlinplayground.coroutines.flow.basics.concurrent_flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest

suspend fun main(): Unit = coroutineScope {

    val flow = flow {
        repeat(5) {
            println("Emitter: Start cooking pancake $it")

            delay(100)
            println("Emitter: pancake $it ready")
            emit(it)
        }
    }.conflate()//similar to state flow

    flow.collect {//
        println("Collector: Start eating pancake $it")
        delay(300)
        println("Collector: Finished eating pancake $it")
    }
}