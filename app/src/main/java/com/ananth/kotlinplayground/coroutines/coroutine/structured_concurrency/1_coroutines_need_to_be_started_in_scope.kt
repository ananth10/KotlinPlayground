package com.ananth.kotlinplayground.coroutines.coroutine.structured_concurrency

import kotlinx.coroutines.*

val scope = CoroutineScope(Dispatchers.Default)
fun main() = runBlocking{
  val job = scope.launch {
       delay(100)
       println("coroutine completed")
   }
    job.invokeOnCompletion {cause: Throwable? ->
       if(cause is CancellationException){
           println("Coroutine was cancelled!")
       }
    }
    delay(50)
    onDestroy()
}

fun onDestroy(){
    println("life time of scope ends now")
    scope.cancel()
}