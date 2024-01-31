package com.ananth.kotlinplayground.generics

import com.ananth.kotlinplayground.Num
import kotlin.Comparator

fun main(){
    check()
}

/**
 * The concept of contravariance can be thought of as a mirror to covariance:
 *  for a contravariant class, the subtyping relation is the opposite of the subtyping relations of classes used as its type arguments.
 *  Let’s start with an example: the Comparator interface. This interface defines one method, compare, which compares two given objects:
 *
 *  You can see that the method of this interface only consumes values of type T. That means T is used only in 'in' positions, and therefore its declaration can be preceded by the in keyword.
 * */

interface Comparator<in T>{
    fun compare(e1:T,e2:T):Int
}

/**
 * A comparator defined for values of a certain type can, of course, compare the values of any subtype of that type.
 * For example, if you have a Comparator<Any>, you can use it to compare values of any specific type.
 * */

val anyComparator = Comparator<Any>{
    e1,e2 -> e1.hashCode() - e2.hashCode()
}
val list = listOf("B","C","D","A")
fun check(){
    list.sortedWith(anyComparator)
    println(list)
}

/**
 * Now you’re ready for the full definition of contravariance. A class that is contravariant on the type parameter is a generic class (let’s consider Consumer<T> as an example)
 * for which the following holds:
 *  Consumer<A> is a subtype of Consumer<B> if B is a subtype of A. The type arguments A and B changed places
 *  so we say the subtyping is reversed. For example, Consumer<Animal> is a subtype of Consumer<Cat>.
 *
 *  A class or interface can be covariant on one type parameter and contravariant on another
 *  The classic example is the Function interface. The following declaration shows a one-parameter Function:
 *
 *  The Kotlin notation (P) -> R is another, more readable form to express Function1<P, R>.
 *  You can see that P (the parameter type) is used only in the in position and is marked with the in keyword
 *  whereas R (the return type) is used only in the out position and is marked with the out keyword.
 *  That means the subtyping for the function type is reversed for its first type argument and preserved for the second.
 *
 *  For example, if you have a higher-order function that tries to enumerate your cats, you can pass a lambda accepting any animals.
 * */

interface Function1<in P, out R>{
    operator fun invoke(p:P):R
}

fun enumerateCats(f:(Cat)->Number){

}

fun Animal.getIndex():Int{
    return 1;
}

fun test(){
    enumerateCats(Animal::getIndex)
}

/**
*Note that in all the examples so far, the variance of a class is specified directly in its declaration and applies to all places where the class is used.
 * Java doesn’t support that and instead uses wildcards to specify the variance for specific uses of a class.
 * Let’s look at the difference between the two approaches and see how you can use the second approach in Kotlin.
 * */

//example

interface Consumer<in T>{

    fun consume(item:T)
}

class Processor<in T> : Consumer<T>{
    override fun consume(item: T) {

    }

}

fun testIn(){
    var anyProcessor:Processor<Any> = Processor()
    val intProcessor:Processor<Int> = Processor()

    anyProcessor.consume("banana")
    intProcessor.consume(12345)

    val stringProcessor:Processor<String> = anyProcessor //assigning super type any to subtype string

    println(stringProcessor.consume("men"))
}