package com.ananth.kotlinplayground.coroutines.flow.exception_handling_cancellation

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

suspend fun main():Unit = coroutineScope {

    launch {
        val stockFlow = stocksFlow()
            .map {
                throw Exception("Exception from map")
            }
        try {
            stockFlow
                .onCompletion { cause ->
                    if(cause==null){
                        println("flow complete successfully")
                    }else{
                        println("flow not completed, see cause: $cause")
                    }
                }
                .collect { stock ->
                println("Collected $stock")
            }
        } catch (e: Exception) {
            println("Handled Exception in catch block")
        }
    }
}

private fun stocksFlow(): Flow<String> = flow {
    emit("Apple")
    emit("MicroSoft")

    throw Exception("Network Request Failed!")
}