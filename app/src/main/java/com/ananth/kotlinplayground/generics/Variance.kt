package com.ananth.kotlinplayground.generics

import com.ananth.kotlinplayground.joinToString

/*The concept of variance describes how types with same base type and different
type arguments relate to each other.
*/
fun main(){
    testVariance1()
}



/**
 * You declare a variable strings of type MutableList<String>. Then you try to pass it to the function. If the compiler accepted it,
 * you’d be able to add an integer to a list of strings, which would then lead to a runtime exception when you tried to access the contents of the list as strings.
 * Because of that, this call doesn’t compile.
 *  */
fun testVariance1(){
    val list = mutableListOf("a","b");
//    modifyList(list) //compiler shows error.
}
fun modifyList(list: MutableList<Any>){
    list.add(0,2);
}

/**
 * below function will work because it just accepts String type but not modify the list
 * */
fun testVariance(){
    val list = listOf<String>("a","b");
//    printContents(list);
}
//below function works for printing
fun printContents(list:List<Any>){
    println(list.joinToString())
}

//checking whether type is a subtype of another

fun f1(key:Int){
    val _key:Number = key;
//    f2(key) compiler error will come, compiler will check everytime.
}

fun f2(key:String){
    println(key)
}

//A non-null version can be subtype of nullable version
fun f3(){
    val name:String = "john"
    val _name:String? = name;

//    name = _name  //compiler will show error.
}