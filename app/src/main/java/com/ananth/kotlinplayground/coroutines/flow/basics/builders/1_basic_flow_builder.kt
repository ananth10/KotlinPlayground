package com.ananth.kotlinplayground.coroutines.flow.basics

import kotlinx.coroutines.flow.*

suspend fun main()
{
    //flowOf
    val first = flowOf(1,2,3,4).collect{ println(it) }

    //asFlow
    val second = listOf(1,2,3,4).asFlow().collect{ println(it) }

    //Flow
    flow{
        emit(1)
        emit("hi")
        emit(true)
    }.collect{ println(it) }
}