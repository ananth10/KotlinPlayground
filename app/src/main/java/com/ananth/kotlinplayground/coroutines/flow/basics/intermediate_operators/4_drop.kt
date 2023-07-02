package com.ananth.kotlinplayground.coroutines.flow.basics.intermediate_operators

import kotlinx.coroutines.flow.*

suspend fun main(){
    val predicate:(Int)->Boolean = {it<3}
    flowOf(1,2,3,4,5)
        .drop(1)
        .dropWhile { predicate(it) }
        .map { transform(it) }
        .take(2)
        .collect{
            println(it)
        }
}