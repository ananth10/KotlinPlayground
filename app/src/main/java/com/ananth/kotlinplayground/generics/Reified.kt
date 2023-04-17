package com.ananth.kotlinplayground.generics

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.security.Provider
import java.security.Provider.Service
import java.util.ServiceLoader

fun main(){

    println(isA<String>("abc"))
    println(isA<String>(123))
    ex1()

//    MainActivity.startActivity<DetailActivity>
}

inline fun <reified T> isA(value:Any) = value is T

//function takes a collection and selects instance of specified class

fun ex1(){
    val items = listOf("abc",1,'b',3)

    val result = items.filterIsInstance<Int>()

    println(result)
}

//ex2 - serviceLoader from java api

fun normalApproach(){
    val serviceImpl = ServiceLoader.load(Provider.Service::class.java)
}

fun usingReified(){
  val serviceLoader = loadService<Service>()
}

inline fun <reified  T> loadService():ServiceLoader<T>{
    return ServiceLoader.load(T::class.java)
}

//simplify the startActivity
inline fun <reified T:Activity> Context.startActivity(){
    val intent = Intent(this,T::class.java)
    startActivity(intent)
}