package com.ananth.kotlinplayground.coroutines.flow.basics.intermediate_operators

import kotlinx.coroutines.flow.*
import java.util.Locale.filter

suspend fun main(){
    val predicate:(Int)->Boolean = {it>1}
    flowOf(1,2,3,4,5)
        .filterNotNull()
        .filterIsInstance<Int>()
        .filter { predicate(it) }
        .filterNot { it==2 }
        .map { transform(it) }
        .collect{
            println(it)
        }
}