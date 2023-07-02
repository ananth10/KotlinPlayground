package com.ananth.kotlinplayground.coroutines.flow.basics.channels

import kotlinx.coroutines.async
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main():Unit = coroutineScope {

    //produce allows to send multiple value to other coroutine
    val channel = produce<Int> {
        println("Sending 10")
        send(10)
        println("Sending 20")
        send(20)
    }

    launch {
       channel.consumeEach {
           println("Received: $it")
       }
    }

}