package com.ananth.kotlinplayground.coroutines.flow.basics.terminaloperators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*


import kotlinx.coroutines.runBlocking

fun main() {

    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)
        println("Emitting second value")
        emit(2)

        delay(100)
        println("Emitting third value")
        emit(1)
    }

    runBlocking {
        val list = flow.toList()
        val set = flow.toSet()
        println(list)
        println(set)
    }

}