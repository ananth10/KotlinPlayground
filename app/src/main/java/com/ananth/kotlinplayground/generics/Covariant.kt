package com.ananth.kotlinplayground.generics

fun main() {

}

//Covariance - preserved subtyping relation
/**
 * Covariance class is a generic class
 * */

open class B {

}

class A : B() {

}

interface Producer<T> {
    fun produce(): T
}

//Producer<A> is subtype of Producer<B>, if A is subtype of B, we can say subtyping is preserved.
//example
//Producer<Cat> is subtype of Producer<Animal> because Cat is subtype of Animal.
//In kotlin, to declare the class to be covariant on a certain type parameter

interface Producer1<out T> {
    fun produce(): T
}

/**
 * Marking a type parameter of a class as covariant makes it possible to pass values of that class as function arguments and return values when the type arguments don’t exactly match the ones in the function definition.
 * For example, imagine a function that takes care of feeding a group of animals, represented by the Herd class.
 * The type parameter of the Herd class identifies the type of the animal in the herd.
 *
 *
 * */

open class Animal {
    fun feed() {

    }
}

class Herd<T : Animal>() {
    val size: Int = 10

    operator fun get(pos: Int) {
//        return list
    }
}

fun feedAll(animals: Herd<Animal>) {
    for (i in 0 until animals.size) {
//      animals[i].feed()
    }
}

//Suppose that a user of your code has a herd of cats and needs to take care of them.

class Cat : Animal(){
    fun cleanLitter(){

    }
}

/**
 * Unfortunately, the cats will remain hungry: if you tried to pass the herd to the feedAll function, you’d get a type-mismatch error during compilation
 * Because you don’t use any variance modifier on the T type parameter in the Herd class.
 * the herd of cats isn’t a subclass of the herd of animals. like List<Int> is not subtype of list<Any>
 * You could use an explicit cast to work around the problem,
 * but that approach is verbose, error-prone, and almost never a correct way to deal with a type-mismatch problem.
 * */
fun takeCareOfCats(cats:Herd<Cat>){
    for (i in 0 until cats.size){
//        cats[i].cleanLitter()
//        feedAll(cats) error
    }
}
/**
 * Because the Herd class has an API similar to List and doesn’t allow its clients to add or change the animals in the herd, you can make it covariant and change the calling code accordingly.
 * */

//covariant Herd class version below

class HerdList<out T : Animal>{
    val size: Int = 10

    operator fun get(pos: Int) {
//        return list
    }
}

fun takeCareOfCat1(cats: HerdList<Cat>){
    for (i in 0 until cats.size){
//        cats[i].cleanLitter()
    }
//    feedAll(cats)
}

/**
 * You can’t make any class covariant:
 * it would be unsafe. Making the class covariant on a certain type parameter constrains the possible uses of this type parameter in the class.
 * To guarantee type safety, it can be used only in so-called out positions,  meaning the class can produce values of type T but not consume them.
 * */

/**
 * Uses of a type parameter in declarations of class members can be divided into in and out positions.
 * Let’s consider a class that declares a type parameter T and contains a function that uses T.
 * We say that if T is used as the return type of a function, it’s in the out position. In this case, the function
 * produces values of type T. If T is used as the type of a function parameter, it’s in the in position. Such a function consumes values of type T
 *
 * */

interface Transformer<T>
{
    fun transform(t:T):T
}

/**
 * The out keyword on a type parameter of the class requires that all methods using T have T only in out positions and not in in positions.
 *  This keyword constrains possible use of T, which guarantees safety of the corresponding subtype relation.
 * As an example, consider the Herd class. It uses the type parameter T in only one place: in the return value of the get method.
 *
 * This is an out position, which makes it safe to declare the class as covariant.
 *  Any code calling get on a Herd<Animal> will work perfectly if the method returns a Cat, because Cat is a subtype of Animal.
 *
 *  To reiterate, the out keyword on the type parameter T means two things:
 *   ->The subtyping is preserved (Producer<Cat> is a subtype of Producer<Animal>).
 *   -> T can be used only in out positions.
 * */

class HerdList1<out T : Animal>{
    val size: Int = 10

    operator fun get(pos: Int):Cat {
        return Cat()
    }
}

/**
 * Now let’s look at the List<T> interface.
 * List is read-only in Kotlin, so it has a method get
 * that returns an element of type T but but doesn’t define any methods that store a value of type T in the list. Therefore, it’s also covariant.
 * */

interface List<out T> : Collection<T>{
    operator fun get(index:Int) : T

    fun subList(fromIndex:Int,toIndex:Int):List<T>

//    fun saveItem(t: T) //show compile error
}

/**
 * Note that a type parameter can be used not only as a parameter type or return type directly, but also as a type argument of another type.
 * For example, the List interface contains a method subList that returns List<T>.
 * In this case, T in the function subList is used in the out position
 *
 * Note that you can’t declare MutableList<T> as covariant on its type parameter,
 *  because it contains methods that take values of type T as parameters and return such values (therefore, T appears in both in and out positions).
 * */

interface MutableList<T> : kotlin.collections.List<T>, kotlin.collections.MutableList<T>{
    override fun add(index: Int, element: T) {
        TODO("Not yet implemented")
    }
}

/**
 * Note that constructor parameters are in neither the in nor the out position. Even if a type parameter is declared as out,
 * you can still use it in a constructor parameter declaration:
 * */

class Heard2<out T:Animal>(vararg animals:T)

/**
 * If you use the val or var keyword with a constructor parameter, however, you also declare a getter
 * and a setter (if the property is mutable). Therefore, the type parameter is used in the out position
 * for a read-only property and in both out and in positions for a mutable property:
 * In below case T cannot marked as out, because the class contains a setter for the leadAnimal property that uses T 'in' position
 * The variance rules protect a class from misuse by external clients and don’t come into play in the implementation of the class itself:
 * */

class Herd3<T:Animal>(var leadAnimal:T,vararg animals:T)

/**
 * Now it’s safe to make Herd covariant on T, because the leadAnimal property has been made private.
 * */

class Herd4<out T:Animal>(private var leadAnimal:T,vararg animals:T)

fun tt(){
    val herd = Herd4<Cat>(Cat())
}