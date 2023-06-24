package com.ananth.kotlinplayground.coroutines.flow.basics.terminaloperators



import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        //only single value to be emitted
//        delay(100)
//        println("Emitting second value")
//        emit(2)
    }

    runBlocking {
        val item = flow.single()
        val item1 = flow.singleOrNull()
        println(item)
    }

}