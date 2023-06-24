package com.ananth.kotlinplayground.coroutines.flow.basics

import java.math.BigInteger

fun main() {
    val startTime  = System.currentTimeMillis()
     calculateFactorial(5).forEach {
        printWithTimePassed(it,startTime)
    }
    println("Sequence is synchronous data stream")
}

private fun calculateFactorial(n: Int): Sequence<BigInteger> = sequence {
    var factorial = BigInteger.ONE
    for (i in 1..n) {
        Thread.sleep(10)
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
       yield(factorial)
    }
}