package com.ananth.kotlinplayground.advanced

fun main(){
    messenger("Hell0"){
        println("message is read")
    }
    "Hi".upSize {
       println(this.uppercase())
    }
}

fun messenger(message:String,action:()->Unit){
    println("message is $message")
    action()
}

fun <T> T.upSize(action:T.()->Unit):T{
    action()
    return this
}