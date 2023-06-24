package com.ananth.kotlinplayground.coroutines.flow.basics.terminaloperators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {

    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)
        println("Emitting second value")
        emit(2)
    }

    runBlocking {
       val item = flow.first() //flow get cancelled once first item received. but if flow dont emit any value then it will produce no such element, so use firstOrNull
//        val item1 = flow.firstOrNull()
        println(item)

        //using predicate
        val item2 = flow.first { it==1 }
    }

}