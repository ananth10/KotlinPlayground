package com.ananth.kotlinplayground.coroutines.flow

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main(){
    runBlocking {
        propagateException()
    }

}

//Exception transparency

/**
 * Exception transparency refers to the ability of a program to correctly handle
 * exceptions in a transparent and predictable manner, without compromising
 * the correctness or safety of the program. in other words
 * exceptions should be able to propagate through code in a way that makes it clear what the error is and where it occurred/
 *
 * ->when we wrap collect function inside try catch block, we are catch any exception that occur inside the collect function.
 * however, this can make it difficult to trace the origin of the exception, as it may have been thrown by a function call inside the collect function.
 * this can lead to confusion and make it difficult to debug.
 * ->Instead the recommended approach is to let exception propagate transparently by not catching them inside the collect function.
 * this way, if an exception is thrown, it will propagate up the call stack and be caught by the calling code, which can handle it appropriately.
 * */

//what is wrong with code
suspend fun whatWrong(){
    try {
        flow.collect{ println(it) }
    }catch (e:Exception){
        println(e)
    }
}

//propagate exception

/**
 * In this example, we have added catch block to the Flow Builder. when the exception thrown,
 * , the catch block catches the exception and emits a value of -1, The downstream receiver receives this value
 * instead of the exception. and the collection process continues.
 *
 * -> this behavior is possible because Coroutine Flow use a cooperative
 * cancellation model, which means that when an exception occurs.
 * the FlowBuilder cooperatively cancels the collection process and propagate
 * the exception to the downstream receiver. this makes handling exception in Coroutine Flows more transparent and less error-prone.
 *
 * */
suspend fun propagateException(){
    val flow = flow{
        emit(1)
        throw RuntimeException("Opps")
        emit(2)
    }.catch { e->
        emit(-1)
    }

    flow.catch { value->
        println(value)
    }
}