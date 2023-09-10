package com.ananth.kotlinplayground.generics.example

fun main(){
    val fish = Animal(Fish())
}

class Animal<T>(val t:T){

    fun produce():T?{
        return null
    }

    fun consume(input:T){

    }
}

fun breed(animal:Animal<out String>){ //use-site variance
  val x = animal.produce() //valid
//    animal.consume("salom") //invalid
}

class Fish{

}

class Bat{

}

/**
 * Sometimes our generic needs to be used in a specific context know as "Variance"
 * There several ways to provide variance in generic type, but in kotlin 2 main approaches
 * 1. Use-site variance
 * 2. Declaration-site variance
 *
 * A type that can be used to provide input and also output that known as invariant(can be consumed and produced)
 * A type that can be only produced is known as Covariant type
 * A type that can be only consumed is known as Contravariant type
 *
 * */

//1. Covariant Type
//A type can only be produced as output

class Greeting<out T>(){

    fun hello():T?{ //valid

    return null
    }

//    fun helloAn(t:T){//invalid
//
//    }
}

//2. Contravariant
//A type can only be consumed as input
class Human<in T>{

    fun men(body:T){ //valid

    }
}


fun <T>display(item:T){
    println(item)
}

fun <T:Mommal> displayMommal(mommal: Mommal){
    println(mommal)
}

interface Mommal{

    fun gestation() : Double
    fun lifeSpan():Int
}

interface Vertebrate{
    val backBone:Boolean
}

//more than one bound

fun <T> dis(item:T) where T:Mommal, T:Vertebrate
{

}

object Human1:Mommal, Vertebrate{
    override fun gestation(): Double {
        TODO("Not yet implemented")
    }

    override fun lifeSpan(): Int {
        TODO("Not yet implemented")
    }

    override val backBone: Boolean
        get() = TODO("Not yet implemented")

}

fun testMoreThanOneBound(){
//    dis(Human1) //wont work without implement Vertebrate interface
}

