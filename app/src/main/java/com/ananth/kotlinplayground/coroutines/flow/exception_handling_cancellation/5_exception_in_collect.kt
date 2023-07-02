package com.ananth.kotlinplayground.coroutines.flow.exception_handling_cancellation

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {

        val stockFlow = stocksFlow()
            .map {
                println("inside map operator")
            }
        stockFlow
            .onCompletion { cause ->
                if (cause == null) {
                    println("flow complete successfully")
                } else {
                    println("flow not completed, see cause: $cause")
                }
            }

            .onEach { stock ->
                println("On Each $stock")
                throw Exception("Exception in on each")
            }.catch { throwable ->
                println("Handle exception in catch: $throwable")
//                emitAll(fallBackFlow())
            }
            .launchIn(this)
}

private fun stocksFlow(): Flow<String> = flow {
    emit("Apple")
    emit("MicroSoft")

    throw Exception("Network Request Failed!")
}
