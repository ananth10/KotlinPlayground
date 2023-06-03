package com.ananth.kotlinplayground.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun main(){
     runBlocking {
         useFlatMapConcat()
         useFlatMapMerge()
         useFlatMapLatest()
     }
}

//Flattening flow

/**
 * Flattening flows is the process of taking flow of flows and transforming it into a single, flat flow
 * that emit all items from the nested flows
 * */

//flat map concat
/**
 * flatmapconcat used when we want to merge multiple flows into a single flow
 * by sequentially concatenating them one after another.
 * It takes a function that transform each emitted value of source flow to
 * another flow and then flattens the resulting flows.
 * this means that the downstream collector receives the values from the resulting flow
 * in the same order they were emitted by the source flow. each transformed flow's values
 * emitted in the order they were emitted.
 *
 * */

suspend fun useFlatMapConcat(){
    val flow1 = flowOf(1,2,3,4)
    val flow2 = flowOf(5,6,7)
    val flow3 = flowOf(8,9,10)

   flowOf(flow1,flow2,flow3).flatMapConcat {it}.collect{print("$it, ")}
}

//flatMapMerge
/**
 * ->flatMapMerge operator is used when we want to merge multiple flows into a single flow without any specific order,
 * meaning that downstream collector may receive values in any order.
 * ->it takes a function that transforms each emitted value from source flow in to another flow
 * and then flattens the resulting flows.
 * ->the resulting flow may emit values from the transformed flows in any order.
 * */

suspend fun useFlatMapMerge(){
    val flow1 = flowOf(1,2,3,4)
    val flow2 = flowOf(5,6,7)
    val flow3 = flowOf(8,9,10)
    println()
    flowOf(flow1,flow2,flow3).flatMapMerge { it }.collect{ print("$it, ") }
}

//flatMapLatest
/**
 * ->flatMapLatest operator is used when we want to transform values emitted by flow into another flow.
 * but only collect value from the most recent transformed flow, and ignore any previous transformed flow.
 * ->It takes function that transform source into other flattening flow.
 * ->when new value emitted the previous transformation cancelled and new transformation used to collect value from the resulting flow.
 * */

suspend fun useFlatMapLatest(){
    val flow1 = flowOf(1,2,3,4)
    val flow2 = flowOf(5,6,7)
    val flow3 = flowOf(8,9,10)
    println()
    flowOf(flow1,flow2,flow3).flatMapLatest { it }.collect{
        print("$it, ")
        delay(100)
    }
}