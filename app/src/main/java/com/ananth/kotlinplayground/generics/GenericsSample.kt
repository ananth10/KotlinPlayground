package com.ananth.kotlinplayground.generics
fun main(){
  val stringList = StringList()
    println(stringList[0])

  val arrayList = ArrayList<Int>()
}

//declare a generic interface

interface List<T>{

    operator fun get(index:Int):T
}

class StringList: List<String>{
    val list = listOf("apple","orange")
    override fun get(index: Int): String {
       return list[index]
    }
}

class ArrayList<E>: List<E>{
    val list = (1..10).toList()

    override fun get(index: Int): E {
        return TODO("Provide the return value")
    }
}


interface Comparable<T>{

    operator fun compareTo(other:T):Int
}
//class itself as type argument

class String1 :Comparable<String1>{
    override fun compareTo(other: String1): Int {
        TODO("Not yet implemented")
    }

}



