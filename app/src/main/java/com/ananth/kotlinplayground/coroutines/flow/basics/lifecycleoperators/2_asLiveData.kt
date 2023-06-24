package com.ananth.kotlinplayground.coroutines.flow.basics.lifecycleoperators

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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
        .asLiveData(Dispatchers.Default) //asLiveData terminal operator cancels flow collection when activity goes in the background and recollect when it comes to foreground
//for asLiveData we dont need to create a coroutine or dont need to pass scope (like we did in launchIn)
    Thread.sleep(1000)
}