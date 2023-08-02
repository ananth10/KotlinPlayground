package com.ananth.kotlinplayground.coroutines.coroutine.structured_concurrency

import kotlinx.coroutines.*

fun main(){
    val scope = CoroutineScope(Job())

    scope.launch {

//        doSomeTask()
        doSomeTask1()
//        coroutineScope {
//            val job1 = launch {
//                println("Starting task 1")
//                delay(100)
//                println("Task 1 completed")
//            }
//            val job2 = launch {
//                println("Starting task 2")
//                delay(100)
//                println("Task 2 completed")
//            }
//        }

//        job1.join()
//        job2.join()
        launch {
            println("Starting task 3")
            delay(100)
            println("Task 3 completed")
        }
    }
    Thread.sleep(500)
}

//this code run in parallel
fun CoroutineScope.doSomeTask(){
    launch {
        println("Starting task 1")
        delay(100)
        println("Task 1 completed")
    }
    launch {
        println("Starting task 2")
        delay(100)
        println("Task 2 completed")
    }
}
//task 1 and task 2 complete before task 3
suspend fun doSomeTask1() = coroutineScope{//or use supervisorScope so when one child fails other one get complete.
    launch {
        println("Starting task 1")
        delay(100)
        println("Task 1 completed")
    }
    launch {
        println("Starting task 2")
        delay(100)
        println("Task 2 completed")
    }
}