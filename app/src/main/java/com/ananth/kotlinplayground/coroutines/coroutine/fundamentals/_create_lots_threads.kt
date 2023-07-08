package com.ananth.kotlinplayground.coroutines.coroutine.fundamentals

import kotlin.concurrent.thread

fun main(){
    repeat(1000000){
        thread {
            Thread.sleep(5000)
            print(".")
        }
    }
}