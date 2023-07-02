package com.ananth.kotlinplayground.coroutines.flow.basics.hot_and_cold_flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    val flow = flow<Int> {
        repeat(5) {
            emit(it)
        }
    }.stateIn(scope, SharingStarted.WhileSubscribed(5000),0)//initial value may be UIState.Loading in real app.
}