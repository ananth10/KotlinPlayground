package com.ananth.kotlinplayground.coroutines.flow.basics.concurrent_flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

suspend fun main():Unit = coroutineScope{

    val flow = flow {
        repeat(5){
            println("Emitter: Start cooking pancake $it")

            delay(100)
            println("Emitter: pancake $it ready")
            emit(it)
        }
    }

    flow.collectLatest{//collect latest used when we mostly care about latest emitted items
        println("Collector: Start eating pancake $it")
        delay(300)
        println("Collector: Finished eating pancake $it")
    }
}