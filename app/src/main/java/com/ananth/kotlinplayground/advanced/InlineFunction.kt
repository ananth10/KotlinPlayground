package com.ananth.kotlinplayground.advanced

fun main() {
    communicate(message = {
        println("message read")
    }, time = {
        1000
    })
}

inline fun communicate(message: () -> Unit, noinline time: () -> Long) {
    println("Message received")
    message()
    println("Time take to process the message is ${time()}")
}