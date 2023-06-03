package com.ananth.kotlinplayground.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        useToSet()
        useToList()
        useCount()
        useReduce()
        useFold()
        useCollect()
    }
}
//Terminal operators
/**
 * A Terminal operator is a function that triggers the Flow's collection process,
 * which causes Flow to emit values and terminate.
 * here are some common terminal operators in flow
 * 1. toList()
 * 2. toSet()
 * 3. count()
 * 4. reduce()
 * 5. fold()
 * 6. collect()
 * */

//1. toList()
/**
 * Collects all the emitted values and returns them as list.
 * This operator is a suspend function and it must be called within coroutine.
 * */

suspend fun useToList() {
    val flow = flowOf(1, 3, 4, null).toList()
    println("LIST:$flow")
}

//2. toSet()
/**
 * Collects all emitted values and returns as set,
 * this operator is a suspend function and it must be called within coroutine.
 * */

suspend fun useToSet() {
    val flow = flowOf(1, 2, 3, 4, null).toSet()
    println("SET:$flow")
}

//3. count()
/**
 * Counts the number of emitted values and returns a single result as an integer.
 * this operator is a suspend function and it must be called within coroutine.
 *
 * */

suspend fun useCount() {
    val flow = flowOf(1, 2, 3, 4, 5).count()
    println("COUNT:$flow")
}

//4. reduce()
/**
 * Applies the binary operation to the emitted values and returns a single result.
 * this operator is a suspend function and it must be called within coroutine.
 * */

suspend fun useReduce() {
    val flow = flowOf(1, 2, 3, 4).reduce { accumulator, value -> accumulator + value }
    println("REDUCE:$flow")
}

//5. fold()
/**
 * Applies a binary operation to the emitted values, with an initial accumulator value, and returns a single result.
 * this operator is a suspend function and it must be called within coroutine.
 * */

suspend fun useFold() {
    val flow = flowOf(1, 2, 3, 4).fold(2) { accumulator, value ->
        accumulator + value
    }

    println("FOLD:$flow")
}

//6. collect()
/**
 * Collects the emitted values and applies the specified action to each one.
 * this operator is a suspend function and it must be called within coroutine.
 * */

suspend fun useCollect() {
    val flow = flowOf(1, 2, 3, 4).collect { println(it * 2) }
}