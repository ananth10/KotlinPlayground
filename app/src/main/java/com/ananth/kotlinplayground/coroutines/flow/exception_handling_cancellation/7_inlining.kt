package com.ananth.kotlinplayground.coroutines.flow.exception_handling_cancellation

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow

suspend fun main():Unit = coroutineScope {
    flow{
        emit(1)
        emit(2)
        emit(3)
    }.collect{ emittedValue->
        println(emittedValue)
    }
}

var inlinedFlow = flow<Int>{
   println("Collected 1")
   println("Collected 2")
   println("Collected 3")
}