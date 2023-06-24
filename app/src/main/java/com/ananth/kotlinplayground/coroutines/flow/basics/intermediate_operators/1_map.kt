package com.ananth.kotlinplayground.coroutines.flow.basics.intermediate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

suspend fun main(){
    flowOf(1,2,3,4,5)
        .mapNotNull { it*2 }
        .map { transform(it) }
        .collect{
            println(it)
        }
}
data class MyNumber(val num:Int)
fun transform(num:Int) = MyNumber(num)