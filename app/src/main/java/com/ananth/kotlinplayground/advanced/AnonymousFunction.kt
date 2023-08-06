package com.ananth.kotlinplayground.advanced

fun main(){
   val random = listOf(1,3,13,56,45,34,-1)
   val result = random.find(fun(num) = num<0)
    println(result)
}

val calculate = fun(a:Int,b:Int):Int{
    return a + b
}