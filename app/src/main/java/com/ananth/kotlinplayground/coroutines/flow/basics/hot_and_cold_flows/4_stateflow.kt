package com.ananth.kotlinplayground.coroutines.flow.basics.hot_and_cold_flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

suspend fun main() {
    val scope = CoroutineScope(EmptyCoroutineContext)
    val counter = MutableStateFlow(0)
    println(counter.value)



    coroutineScope {
        repeat(10000) {
            launch {
                counter.update { currentValue ->
                    currentValue + 1
                }
            }
        }
    }
    println(counter.value)
    counter.emit(1)
    counter.emit(2)
    counter.emit(2)
    println(counter.value)
}