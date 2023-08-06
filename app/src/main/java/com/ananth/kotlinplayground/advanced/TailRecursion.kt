package com.ananth.kotlinplayground.advanced

fun main() {
    println("5! = ${factorial(5)}")
    println("5! = ${factorialLoop(5)}")
    recursivePrint(5)
    val result = factorialTailRec(5, 1)
    println("Result :$result")
}

//Recursive
fun factorial(num: Long): Long {
    return if (num <= 1) {
        1
    } else {
        num * factorial(num - 1)
    }
}

//Iterative

fun factorialLoop(num: Long): Long {
    var fact = 1L
    var count = 2

    while (count <= num) {
        fact *= count
        count++
    }
    return fact
}

//tail recursion - Recursive call is the very last call in a recursive function

fun recursivePrint(n: Int) {
    if (n < 0) {
        return
    }
    println("number is $n")
    recursivePrint(n - 1)
}

//recursive factorial

tailrec fun factorialTailRec(n: Int, tot: Int): Int {
    val temp = tot * n
    return if (n <= 1) {
        temp
    } else {
        factorialTailRec(n - 1, temp)
    }
}