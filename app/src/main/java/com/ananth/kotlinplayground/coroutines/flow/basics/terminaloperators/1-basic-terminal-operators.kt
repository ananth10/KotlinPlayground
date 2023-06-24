package com.ananth.kotlinplayground.coroutines.flow.basics.terminaloperators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun main() {
    //flow declaration, there is no one to collect it, and flow calling two suspend functions delay and emit and flow is not called from coroutine, so this flow simply cannot run
    //so we can call flow by using terminal operator on the flow, we already used collect terminal operator
    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)
        println("Emitting second value")
        emit(2)
    }

    val list = buildList{
        add(1)
        println("add 1 to list")

        add(2)
        println("add 2 to list")

    }
}