package com.ananth.kotlinplayground

fun main(){
    val lateInitTest = LateInitTest()
    lateInitTest.initUser()
    println(lateInitTest.getFood())

   val lazy = LazyTest()
   lazy.getFood1()
}


data class Food(val breakFast:String,val lunch:String,val dinner:String)


class LateInitTest{
    private lateinit var food: Food


    fun initUser(){
       food = Food("Idly","Meals","Chapathi")
    }

    fun getFood() = food
}

class LazyTest{


    private val food: Food by lazy {
        Food("Idly","Meals","Chapathi")
    }

    fun getFood1(): Food {
        return food
    }
}