package com.ananth.kotlinplayground.coroutines.flow.basics.concurrent_flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

suspend fun main(): Unit = coroutineScope {

    val flow = MutableStateFlow<Int>(0)
    //collector 1
    launch {
        flow.collect{
            println("Collector 1 processes $it")
        }
    }
    //collector 2
    launch {
        flow.collect{
            println("Collector 2 processes $it")
            delay(100)
        }
    }

    //Emitter
    launch {
        val timeToEmit = measureTimeMillis {
            repeat(5){
                flow.emit(it)
                delay(10)
            }
        }
        println("Time to emit all items $timeToEmit")
    }
}