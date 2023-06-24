package com.ananth.kotlinplayground.coroutines.flow.basics.terminaloperators

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

fun main(){
    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)
        println("Emitting second value")
        emit(2)
    }
    val scope = CoroutineScope(EmptyCoroutineContext)
    flow.onEach { println(it) } //need to use onEach to collect emitted value from the flow
        .launchIn(scope) //to internally start a new coroutine in given scope and collect from the flow
    //basically short cut for scope.launch{flow.collect{}}

    Thread.sleep(1000)//added this, because the main function will not shutdown till 1000ms
}

//launchIn vs launch

/**
 * launchIn is create a separate coroutine and collect value from flow, even if we use launchIn in same flow. so 2 coroutines will be created and both are collect value from same flow in parallel
 * but launch is sequential, it will wait until first flow emitted data collected
 * */

fun launchIn(){
    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)
        println("Emitting second value")
        emit(2)
    }
    val scope = CoroutineScope(EmptyCoroutineContext)
    //1
    flow.onEach { println(it) }
        .launchIn(scope)
    //2
    flow.onEach { println(it) }
        .launchIn(scope)

    scope.launch {
        //1
        flow.collect{ println(it) }

        //2
        flow.collect{ println(it) }
    }
}