package com.ananth.kotlinplayground.coroutines.coroutine.fundamentals

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { coroutine(1,500) },
        async { coroutine(2,400) }
    )
    println("main ends")
}
//Coroutines are cooperative routines
//1.suspend functions that perform some long running operation(s) and can be suspended.
//2.and can only be called from other suspend function or coroutine
//3.suspend functions can be paused at Suspension points
private suspend fun coroutine(number:Int, delay:Long){
    println("Coroutine $number starts work")
    println("Coroutine $number has finished")
//    delay(delay)
    Handler(Looper.getMainLooper()).postDelayed({
    },delay)

}