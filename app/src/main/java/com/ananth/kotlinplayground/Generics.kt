package com.ananth.kotlinplayground

import java.lang.Appendable
import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import kotlin.reflect.KClass

fun main(){

    val helloWorld= StringBuilder("Hello World")
    ensureTrailingPeriod(helloWorld)
    println(helloWorld)

    val list:List<String> = listOf("a","b")
    val list1= listOf("a","b")
//        printSum(setOf("a","b"))
    printSum1(listOf(1,2,3))
    println(isA<String>("1"))
    println(isA<String>(1))

    val items= listOf("a",1,"b")
    println(items.filterIsInstanceSample<String>())
    printContent(listOf("a","b","c"))
    val list2  = mutableListOf("a","B")
    addMore(list2)
    copyData()

    val names= mutableListOf("Ananth","babu")
    printFirst(names)

    val numList = (1..100).toList().CustomSum { it%2==1 }
    println("$numList")
}

inline fun <reified T> isA(value:Any)=value is T

inline fun <reified T> Iterable<*>.filterIsInstanceSample():List<T>{
    val dest= mutableListOf<T>()
    for (element in this){
        if(element is T){
            dest.add(element)
        }
    }
    return dest
}

fun <T:Number> oneHalf(value:T):Double{
    return value.toDouble()/2.0
}

fun<T:Comparable<T>> max(first:T,second:T):T{
    return if(first>second)first else second
}

fun <T> ensureTrailingPeriod(seq:T) where  T:CharSequence, T: Appendable {
    if(!seq.endsWith(".")){
        seq.append(".")
    }
}

class Processor<T:Any>{
    fun process(value:T){
        value?.hashCode()
    }
}

fun printSum(list:Collection<*>){
    val intList = list as? List<Int>?:throw IllegalArgumentException("List is expected")
    println(intList.sum())
}

fun printSum1(list:Collection<Int>){
    if(list is List<Int>){
        println(list.sum())
    }
}

fun printContent(list:List<Any>){
    println(list.joinToString())
}

fun addMore(list:List<Any>){
//        list.add(42)
}

fun <T:R, R> copyData(source:MutableList<T>, destination:MutableList<R>){
    for (item in source){
        destination.add(item)
    }
}

fun copyData(){
    val sourceList= mutableListOf(1,2,3)
    val dest= mutableListOf<Any>()
    println("RES::$dest")
    copyData(sourceList,dest)
}

// MutableList<out T> = MutableList<? extends T> in kotlin covarient type and in java its upper bounded wildcard

//MutableList<in T> = MutableList<? super T> in kotlin contravarient and in java its lower bounded wildcard

// MyType<*> in kotlin corresponds to Java's MyType<?>

fun printFirst(list:List<*>){

    if(list.isNotEmpty()){
        println(list.first())
    }
    Validators.registerValidator(String::class, DefaultStringValidator)
    Validators.registerValidator(Int::class, DefaultIntValidator)



}

object Validators{

    private val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()

    fun <T:Any> registerValidator(KClass: KClass<T>, fieldValidator: FieldValidator<T>){
        validators[KClass]=fieldValidator
    }

    operator fun <T: Any> get(KClass: KClass<T>): FieldValidator<T> =
        validators[KClass] as? FieldValidator<T> ?:throw IllegalArgumentException("No validator for ${KClass.simpleName}")

}


interface FieldValidator<in T>{
    fun validate(input:T):Boolean
}

object  DefaultStringValidator : FieldValidator<String> {
    override fun validate(input: String)=input.isNotEmpty()

}

object  DefaultIntValidator : FieldValidator<Int> {
    override fun validate(input: Int)=input>=0

}

fun  List<Int>.CustomSum(sumFunction:(Int)->Boolean):Int{
    var sum = 0
    for (number in this){
        if(sumFunction(number)){
            sum += number
        }
    }

    return sum
}
