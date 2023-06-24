package com.ananth.kotlinplayground.coroutines.flow.basics

import java.math.BigInteger

fun main() {
    val startTime  = System.currentTimeMillis()
    val result = calculateFactorial(5).forEach {
        printWithTimePassed(it,startTime)
    }
    println("Result: $result")
}

private fun calculateFactorial(n: Int): List<BigInteger> = buildList {
    var factorial = BigInteger.ONE
    for (i in 1..n) {
        Thread.sleep(10)
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        add(factorial)
    }
}