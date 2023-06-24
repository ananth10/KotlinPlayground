package com.ananth.kotlinplayground.coroutines.flow.basics

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.math.BigInteger

fun main() = runBlocking{
    val startTime  = System.currentTimeMillis()
        launch {
            calculateFactorial(5).collect {
                printWithTimePassed(it,startTime)
            }
        }
    println("Flow is asynchronous")
}

private fun calculateFactorial(n: Int): Flow<BigInteger> = flow {
    var factorial = BigInteger.ONE
    for (i in 1..n) {
       delay(10)
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        emit(factorial)
    }
}.flowOn(Dispatchers.Default)