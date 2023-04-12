package com.ananth.kotlinplayground



fun main(){

}

/**
 * Reification is the process by which an abstract idea about an application is turned into an explicit data model or other object are created in a programming language.
 * */

fun <T> createFromJson(jsonString: String){
    // Error. Cannot use 'T' as reified type parameter
//    return Gson().fromJson(jsonString, T::class.java)

}

