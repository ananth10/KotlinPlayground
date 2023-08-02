package com.ananth.kotlinplayground.coroutines.coroutine.structured_concurrency

import kotlinx.coroutines.*

fun main(){
    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default+scopeJob)

    var childJob:Job? = null
    val passedJob = Job()
    val cjob = scope.launch(passedJob) {
        childJob = launch {
            println("Starting child coroutine")
            delay(100)
        }
        println("Starting coroutine")
        delay(100)
    }
    println("is this child of scopeJob=>${scopeJob.children.contains(cjob)}")
    println("is this child of cjob=>${cjob.children.contains(childJob)}")
    println("is this passed job and cjob are references to the same job object=>${passedJob==cjob}")
    Thread.sleep(500)
}