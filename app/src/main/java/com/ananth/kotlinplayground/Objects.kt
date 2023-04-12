package com.ananth.kotlinplayground

import java.util.Comparator

fun main(){

    val strings = listOf("Hello","World")
    val sortedStrings = strings.sortedWith(ReverseStringComparator)
    println(sortedStrings)
}
object TestObjects {
    private var count = 0
    val total = 20

    fun testFunction(name: String) = "Hello $name"

    fun currentCount()= count

    fun countIncrement(){
        count++
    }

}

object ReverseStringComparator : Comparator<String> {
    override fun compare(o1: String, o2: String) = o1.reversed().compareTo(o2.reversed())
}