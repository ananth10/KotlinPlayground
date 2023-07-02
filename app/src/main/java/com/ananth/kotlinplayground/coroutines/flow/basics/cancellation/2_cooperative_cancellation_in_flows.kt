package com.ananth.kotlinplayground.coroutines.flow.basics.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.coroutines.EmptyCoroutineContext

suspend fun main() {
    val scope = CoroutineScope(EmptyCoroutineContext)
    scope.launch {
        intFlow()
            .onCompletion { throwable ->
                if (throwable is CancellationException) {
                    println("Flow got cancelled")
                }
            }
            .collect {
                println("Collected $it")

                if (it == 2) {
                    cancel()
                }
            }
    }.join()//main function wait till the coroutine get completes
}

private fun intFlow() = flow {
    emit(1)
    emit(2)
    currentCoroutineContext().ensureActive()//it will check the current coroutine has cancelled or not.
    println("Start calculation")
    calculateFactorial(1000)
    println("End calculation")
    emit(3)
}

private suspend fun calculateFactorial(n: Int): BigInteger = coroutineScope {
    var factorial = BigInteger.ONE
    for (i in 1..n) {
        Thread.sleep(10)
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        ensureActive()
    }
   factorial
}