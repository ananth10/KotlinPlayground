package com.ananth.kotlinplayground.coroutines.flow.basics

import java.math.BigInteger

fun main() {
    val result = calculateFactorial(5)
    println("Result: $result")
}

private fun calculateFactorial(n: Int): BigInteger {
    var factorial = BigInteger.ONE
    for (i in 1..n) {
        Thread.sleep(10)
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
    }
    return factorial
}