package com.ananth.kotlinplayground.coroutines.flow.basics.lifecycleoperators

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.coroutines.EmptyCoroutineContext

fun main() {
    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)
        println("Emitting second value")
        emit(2)
    }
    val scope = CoroutineScope(EmptyCoroutineContext)
    //1. this construct called as flow processing pipeline

    flow
        .map {
            //we can transform original flow into UIState
        }
        .onStart {
        //we can show progress indicator, UIState.Loader()
//        emit(UiState.Loading)
        println("flow started")
    }.onCompletion { causes ->
        //flow completed
        println("flow completed:${causes?.message}")
    }
    .onEach { println(it) }
        .onStart {
            println("flow start end")
        }
    .launchIn(scope)

    Thread.sleep(1000)
}