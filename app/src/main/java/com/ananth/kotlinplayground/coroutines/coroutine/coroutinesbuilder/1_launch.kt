package com.ananth.kotlinplayground.coroutines.coroutine.coroutinesbuilder

import kotlinx.coroutines.*

//3 types of coroutine builder
//1. launch 2. async 3. runBlocking

//without using runBlocking
//fun main(){
//    GlobalScope.launch {
//        delay(1000)
//        println("Hi:"+Thread.currentThread().name)
//    }
//    Thread.sleep(1500)
//}

//fun main(){
//    runBlocking {
//        launch {
//            delay(400)
//            println("Hi")
//        }
//    }
//}

//eagerly coroutine
//fun main(){
//    runBlocking {
//        val job = launch {
//            delay(400)
//            println("Hi")
//        }
//        job.join()
//        println("End of run blocking")
//    }
//}

//lazy coroutine
fun main(){
    runBlocking {
        val job = launch(start = CoroutineStart.LAZY) {
            delay(400)
            println("Hi")
        }
        delay(200)
        job.start()
        job.join()
        println("End of run blocking")
    }
}
suspend fun networkRequest():String{
    delay(500)
    return "welcome"
}
