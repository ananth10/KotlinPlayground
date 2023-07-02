package com.ananth.kotlinplayground.coroutines.flow.basics.intermediate_operators

import kotlinx.coroutines.flow.*

suspend fun main(){
    val predicate:(Int)->Boolean = {it<3}
    flowOf(1,2,3,4,5)
        .transform{
            emit(it)
            emit(it*10)
        }
        .collect{
            println(it)
        }
}