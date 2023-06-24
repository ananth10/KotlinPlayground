package com.ananth.kotlinplayground.coroutines.flow.basics

fun printWithTimePassed(message: Any?, startTime: Long) {
    val timePassed = System.currentTimeMillis() - startTime
    print("${timePassed}ms: ")
    println(message)
}

var lastPrintTime = System.currentTimeMillis()
fun printWithLastPrintTime(message: Any?) {
    val timePassed = System.currentTimeMillis() - lastPrintTime
    print("$timePassed: ")
    println(message)
    lastPrintTime = System.currentTimeMillis()
}

fun getStartTime(timeUntilFirstFlowEmission: Long) = System.currentTimeMillis() + timeUntilFirstFlowEmission
