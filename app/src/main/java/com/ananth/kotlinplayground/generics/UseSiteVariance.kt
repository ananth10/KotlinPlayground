package com.ananth.kotlinplayground.generics

import com.ananth.kotlinplayground.R
import com.ananth.kotlinplayground.copyData

fun main(){
    test2()
}

/**
 * The ability to specify variance modifiers on class declarations is convenient because the modifiers apply to all places where the class is used.
 * This is called declaration-site variance. If you’re familiar with Java’s wildcard types (? extends and ? super)
 * you’ll realize that Java handles variance differently.
 *  In Java, every time you use a type with a type parameter, you can also specify whether this type parameter can be replaced with its subtypes or supertypes.
 *  This is called use-site variance.
 * */

//Declaration site variance in Kotlin Vs Java

//Java
/**
 * 1. Declaration-site variance allows for more concise code, because you specify the variance modifiers once, and clients of your class don’t have to think about them.
 * 2. In Java, to create APIs that behave according to users’ expectations,
 * 3. the library writer has to use wildcards all the time: Function<? super T, ? extends R>.
 * 4.  If you examine the source code of the Java 8 standard library, you’ll find wildcards on every use of the Function interface.
 * 5. For example, here’s how the Stream.map method is declared:
 * 6. Specifying the variance once on the declaration makes the code much more concise and elegant.
 * */
/* Java */
public interface Stream<T> {
//    <R> Stream<R> map(Function<? super T, ? extends R> mapper);
}

//Kotlin

/**
 * 1.Kotlin supports use-site variance too
 * 2. allowing you to specify the variance for a specific occurrence of a type parameter even when it can’t be declared as covariant or contravariant in the class declaration. Let’s see how that works.
 * 3. You’ve seen that many interfaces, like MutableList, aren’t covariant or contravariant in a general case
 * 4. because they can both produce and consume values of types specified by their type parameters.
 * But it’s common for a variable of that type in a particular function to be used in only one of those roles:
 * either as a producer or as a consumer. For example, consider this simple function.
 *
 * the element types of the collections do not need to match exactly.
 * for example, It's perfectly valid to copy a collection of String type into a collection that can contain any objects
 * */
//A data copy function with invariant parameter type
fun <T> ownCopyData(source:kotlin.collections.MutableList<T>,destination:kotlin.collections.MutableList<T>){
    for (item in source){
        destination.add(item)
    }
}
//To make this function work with lists of different types, you can introduce the second generic parameter.

fun <T : R, R> ownCopyData1(
    source: kotlin.collections.MutableList<T>,
    destination: kotlin.collections.MutableList<R>
) {
    for (item in source) {
        destination.add(item)
    }
}

fun test2() {
    val ints = mutableListOf(1, 2, 3)

    val anyItems = mutableListOf<Any>()
    ownCopyData1(ints, anyItems)
    println(anyItems)
}

/**
 * You can specify a variance modifier on any usage of a type parameter in a type declaration:
 *  What happens here is called type projection:
 *  we say that source isn’t a regular MutableList, but a projected (restricted) one.
 * */
//A data copy function with an out-projected type parameter
fun <T : R, R> ownCopyData2(
    source: kotlin.collections.MutableList<out T>,
    destination: kotlin.collections.MutableList<R>
) {
    for (item in source) {
        destination.add(item)
    }
}
/**
 * In a similar way, you can use the in modifier on a usage of a type parameter to indicate that in this
 * particular location the corresponding value acts as a consumer,
 *  and the type parameter can be substituted with any of its supertypes.
 * */

fun <T> ownCopyData3(
    source: kotlin.collections.MutableList<T>,
    destination: kotlin.collections.MutableList<in T>
) {
    for (item in source) {
        destination.add(item)
    }
}

/**
 * Use-site variance declarations in Kotlin correspond directly to Java bounded wildcards.
 * MutableList<out T> in Kotlin means the same as MutableList<? extends T> in Java.
 * The in-projected MutableList<in T> corresponds to Java’s MutableList<? super T>.
 * Use-site projections can help to widen the range of acceptable types.
 * */