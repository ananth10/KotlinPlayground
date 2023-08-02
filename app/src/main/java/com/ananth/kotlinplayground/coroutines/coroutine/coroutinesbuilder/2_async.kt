package com.ananth.kotlinplayground.coroutines.coroutine.coroutinesbuilder

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val deferred1 = async {
        delay(500)
        "1"
    }

    val deferred2 = async {
        delay(500)
        "2"
    }

    val result = listOf(deferred1.await(),deferred2.await())
    println("Result: $result")
}

//Lazy async, so we can call coroutine later time whenever needed.

//fun main() = runBlocking {
//
//    val deferred1 = async(start = CoroutineStart.LAZY) {
//        delay(500)
//        "1"
//    }
//
//    val deferred2 = async(start = CoroutineStart.LAZY) {
//        delay(500)
//        "2"
//    }
//
//    delay(100)
//    deferred1.start() //starting coroutine manually
//
//    delay(100)
//    deferred2.start() //starting coroutine manually
//
//    val result = listOf(deferred1.await(),deferred2.await())
//    println("Result: $result")
//}