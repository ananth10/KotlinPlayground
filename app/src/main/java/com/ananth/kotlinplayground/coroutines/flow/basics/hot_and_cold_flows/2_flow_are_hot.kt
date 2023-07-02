package com.ananth.kotlinplayground.coroutines.flow.basics.hot_and_cold_flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

fun main(){
    val sharedFlow = MutableSharedFlow<Int>()

    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        repeat(5){
            println("SharedFlow emits $it")
            sharedFlow.emit(it)
            delay(200)
        }
    }

    Thread.sleep(500)
    scope.launch {
        sharedFlow.collect{
            println("Collected $it")
        }
    }
    Thread.sleep(1500)
}
//1. Hot flow are active regardless of whether there are collectors
//2. With hot flow, there is possible to loss some emitted value
//3. Emissions are shared between all collectors
