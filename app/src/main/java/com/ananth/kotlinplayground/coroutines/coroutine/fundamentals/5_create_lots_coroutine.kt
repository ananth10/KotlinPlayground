package com.ananth.kotlinplayground.coroutines.coroutine.fundamentals

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    repeat(1000000) {
    launch {
        delay(5000)
        print(".")
    }
}
}
