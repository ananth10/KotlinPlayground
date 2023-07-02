package com.ananth.kotlinplayground.coroutines.flow.exception_handling_cancellation

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

suspend fun main():Unit = coroutineScope {

    launch {
        val stockFlow = stocksFlow()
            .map {
                println("inside map operator")
            }
            stockFlow
                .onCompletion { cause ->
                    if(cause==null){
                        println("flow complete successfully")
                    }else{
                        println("flow not completed, see cause: $cause")
                    }
                }
                .catch {throwable->
                  println("Handle exception in catch: $throwable")
                }
                .collect { stock ->
                    println("Collected $stock")
                }
    }
}

private fun stocksFlow(): Flow<String> = flow {
    emit("Apple")
    emit("MicroSoft")

    throw Exception("Network Request Failed!")
}