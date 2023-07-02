package com.ananth.kotlinplayground.coroutines.flow.basics.intermediate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.withIndex

suspend fun main(){
    val predicate:(Int)->Boolean = {it<3}
    flowOf(1,2,3,4,5)
        .withIndex()
        .collect{
            println(it)
        }
}