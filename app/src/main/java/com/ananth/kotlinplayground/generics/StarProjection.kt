package com.ananth.kotlinplayground.generics

import java.util.*
import kotlin.reflect.KClass


fun main(){
    tuck1()
}

/**
 * the special star-projection syntax you can use to indicate that you have no information about a generic argument.
 * First, note that MutableList<*> isn’t the same as MutableList<Any?> (it’s important here that MutableList<T> is invariant on T).
 *  A MutableList<Any?> is a list that you know can contain elements of any type.
 *  On the other hand, a Mutable-List<*> is a list that contains elements of a specific type, but you don’t know what type it is.
 *  The list was created as a list of elements of a specific type, such as String (you can’t create a new ArrayList<*>)
 *  and the code that created it expects that it will only contain elements of that type.
 *  Because you don’t know what the type is, you can’t put anything into the list,
 *  because any value you put there might violate the expectations of the calling code.
 *  But it’s possible to get the elements from the list, because you know for sure that all values stored there will match the type Any?,
 *  which is the supertype of all Kotlin types:
 * */

fun testStar(){
    val list:kotlin.collections.MutableList<Any?> = mutableListOf(1,"a",false)
    val chars = mutableListOf('a','b','c')
    val unknownElements:kotlin.collections.MutableList<*> = if(Random().nextBoolean()) list else chars
//    unknownElements.add(3) //compiler throw errors
}

/**
 * Why does the compiler refers to MutableList<*> as an out-projected type? In this context,
 * MutableList<*> is projected to (acts as) MutableList<out Any?>:
 * when you know nothing about the type of the element, it’s safe to get elements of Any? type,
 * but it’s not safe to put elements into the list.
 * Speaking about Java wildcards, MyType<*> in Kotlin corresponds to Java’s MyType<?>.
 *
 * For contravariant type parameters such as Consumer<in T>, a star projection is equivalent to <in Nothing>.
 * In effect, you can’t call any methods that have T in the signature on such a star projection.
 * If the type parameter is contravariant, it acts only as a consumer, and,
 * as we discussed earlier, you don’t know exactly what it can consume.Therefore, you can’t give it anything to consume
 * */

/**
 * You can use the star-projection syntax when the information about type arguments isn’t important:
 * you don’t use any methods that refer to the type parameter in the signature, or you only read the
 * or you only read the data and you don’t care about its specific type. For instance,
 * you can implement the printFirst function taking List<*> as a parameter:
 * */

fun printFirst(list:kotlin.collections.List<*>){
    if(list.isNotEmpty()){
        println(list.first())
    }
}

//As in the case with use-site variance, you have an alternative—to introduce a generic type parameter:

fun <T> printFirst1(list:kotlin.collections.List<T>){
    if(list.isNotEmpty()){
        println(list.first())
    }
}

//Another trap example
interface FieldValidator<in T>{
    fun validate(input:T):Boolean
}
object DefaultStringValidator : FieldValidator<String> {
    override fun validate(input: String): Boolean {
       return input.isNotEmpty();
    }
}

object DefaultIntValidator : FieldValidator<Int>{
    override fun validate(input: Int): Boolean {
       return input>0;
    }
}

/**
 * Now imagine that you want to store all validators in the same container and get the right validator according to the type of input.
 * Your first attempt might use a map to store them. You need to store validators for any types,
 *  so you declare a map from KClass (which represents a Kotlin class to FieldValidator<*> (which may refer to a validator of any type):
 *
 *  Once you do that, you may have difficulties when trying to use the validators.
 * */

fun saveValidators(){
    val validators = mutableMapOf<KClass<*>,FieldValidator<*>>()
    validators[String::class] = DefaultStringValidator
    validators[Int::class] = DefaultIntValidator

//    validators[String::class].validate("") //compiler show Error: Out-projected type 'FieldValidator<*>' prohibits

    //we show it here as a fast trick to make your code compile so that you can refactor it afterward.

    val stringValidator = DefaultStringValidator as FieldValidator<String>
    stringValidator.validate("")

    //above solution isn’t type-safe and is error-prone.
}

/**
 * uses the same validators map but encapsulates all the access to it into
 * two generic methods responsible for having only correct validators registered and returned.
 *  This code also emits a warning about the unchecked cast (the same one),
 *  but here the object Validators controls all access to the map,
 *  which guarantees that no one will change the map incorrectly.
 *
 *  Now you have a type-safe API. All the unsafe logic is hidden in the body of the class; and by
 *  localizing it, you guarantee that it can’t be used incorrectly. The compiler forbids you to use an
 *  incorrect validator, because the Validators object always gives you the correct validator
 * */

object Validators{
    private val validators = mutableMapOf<KClass<*>,FieldValidator<*>>()

    fun <T:Any> registerValidator(kClass: KClass<T>,fieldValidator: FieldValidator<T>){
        validators[kClass] = fieldValidator;
    }

    operator fun <T:Any> get(kClass: KClass<T>):FieldValidator<T>{
       return validators[kClass] as? FieldValidator<T>
            ?: throw IllegalArgumentException(
                "No validator for ${kClass.simpleName}")
    }
}

fun tuck1(){
    Validators.registerValidator(String::class, DefaultStringValidator)
    Validators.registerValidator(Int::class, DefaultIntValidator)

    println(Validators[String::class].validate("Kotlin"))
//    println(Validators[String::class].validate(23)) //we cannot pass incorrect type

}

/**
 * Summary
Kotlin’s generics are fairly similar to those in Java: you declare a generic function or class in the same way.
As in Java, type arguments for generic types only exist at compile time.
You can’t use types with type arguments together with the is operator, because type arguments are erased at runtime.
Type parameters of inline functions can be marked as reified, which allows you to use them at runtime to perform is checks and obtain java.lang.Class instances.
Variance is a way to specify whether one of two generic types with the same base class and different type arguments is a subtype or a supertype of the other one if one of the type arguments is the subtype of the other one.
You can declare a class as covariant on a type parameter if the parameter is used only in out positions.
The opposite is true for contravariant cases: you can declare a class as contravariant on a type parameter if it’s used only in in positions.
The read-only interface List in Kotlin is declared as covariant, which means List<String> is a subtype of List<Any>.
The function interface is declared as contravariant on its first type parameter and covariant on its second, which makes (Animal)->Int a subtype of (Cat)->Number.
Kotlin lets you specify variance both for a generic class as a whole (declaration-site variance) and for a specific use of a generic type (use-site variance).
The star-projection syntax can be used when the exact type arguments are unknown or unimportant.
 * */




