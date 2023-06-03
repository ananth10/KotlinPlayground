package com.ananth.kotlinplayground.coroutines.flow

import kotlinx.coroutines.flow.*


fun main(){

}
//Transformation operators
/**
 * Coroutine Flow provides a variety of transforming operators for coroutine flows.
 * These operators allows you to create a new flows by transforming data emitted by a source flow.
 * most used transform operators are, map, filter, transform, zip,
 * */

//1. map
/**
 * A map operator applies a transformation function to each element
 * emitted by source flow and emit transformed elements into downstream
 * */

suspend fun mapUse() {
    val flow = (1..5).asFlow()
    val result = flow.map { it * 2 }.collect { println(it) }
}

//2. Filter
/**
 * A Filter operator filters elements emitted by source flow using predicate function
 * and emits elements only that satisfy the predicate
 * */

suspend fun useFilter() {
    val flow = (1..100).asFlow()
    val result = flow.filter { it > 20 }.collect { println(it) }
}

//3. Transform

/**
 * The transform operator allows you to perform arbitrary transformation on data emitted by source flow.
 * the transformation function takes value and FlowCollector interface and emit zero or more values to collector.
 *  The transform operator transforms each value into two strings ("A$value" and "B$value") and emits them to downstream.
 * */

suspend fun useTransform() {
    val flow = (1..100).asFlow()
    flow.transform { value ->
        emit("A$value")
        emit("B$value")
    }.collect { println(it) }
}

//4. Zip
/**
 * The zip operator combines two flows into a single flow of pairs.
 * each pair contain one element from each source flow
 * so, below example , a flow emits value from 1 to 10 and b emits value from 11 to 20
 * and the zip operator combines these flows into a single flow of pairs and emits the sum of each pair to downstream.
 *
 * */

suspend fun useZip() {
    val a = (1..10).asFlow()
    val b = (11..20).asFlow()
    a.zip(b) { x, y -> x + y }.collect { println(it) }
}