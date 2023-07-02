package com.ananth.kotlinplayground.coroutines.flow.exception_handling_cancellation

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

suspend fun main(): Unit = coroutineScope {
    flow {
        emit("hi")
    }.catch {
        print("Exception catch")
    }.collect { emittedValue ->
            throw Exception("Exception in collect")
        }
}