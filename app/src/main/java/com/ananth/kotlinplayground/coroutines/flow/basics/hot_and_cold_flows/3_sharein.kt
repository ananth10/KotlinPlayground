package com.ananth.kotlinplayground.coroutines.flow.basics.hot_and_cold_flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    val flow = flow<Int> {
        repeat(5) {
            emit(it)
        }
    }.shareIn(
        scope,
        SharingStarted.WhileSubscribed(5000),//it will start and stop when activity come foreground and background, and flow are shared across multiple collector.
        //wait for 5000ms before stopping collection from upstream flow
        replay = 1
    )
}