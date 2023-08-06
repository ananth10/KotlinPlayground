package com.ananth.kotlinplayground.advanced

import android.annotation.SuppressLint

@SuppressLint("SuspiciousIndentation")
fun main() {
    val coordinates = Coordinates(10, 30, -90)
    println(coordinates)
    println(-coordinates)

    val cor1 = Coordinates(1,2,3)
    val cor2 = Coordinates(1,2,3)
    println("Plus:${cor1+cor2}")
    School()
}
//rules for implementing operator overloading
/**
 * 1. all operator functions must be member functions or extension function
 * */

//Unary operator - only walks on one variable e.g -a , !boolean
//!isShown() -> isShown.not()
//-num -> num.unaryMinus()


//Plus operator
data class Coordinates(val x: Long, val y: Long, val z: Long) {
    operator fun unaryMinus() = Coordinates(-x, -y, -z)

    operator fun plus(coordinates: Coordinates): Coordinates {
        val (x1, y1, z1) = coordinates
        return Coordinates(x + x1, y + y1, z + z1)
    }
}


//Invoke operator

object School{

    operator fun invoke(){
        println("going to school")
    }
}