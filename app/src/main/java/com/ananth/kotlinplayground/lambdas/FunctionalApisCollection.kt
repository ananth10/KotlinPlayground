package com.ananth.kotlinplayground.lambdas

fun main() {
    useFilter()
    useMap()

    checkPredicate()
    testGroupBy()

    testFlatMap()

    testSeq()
}

/**
 * Functional style provides many benefits when it comes to manipulating collections.
 * */
//Essentials: filter and map

/**
 * The filter and map functions form the basis for manipulating collections. Many collection operations can be expressed with their help.
 *
 * ->filter function goes through a collection and selects the elements for which the given lambda returns true.
 * ->The result is a new collection that contains only the elements from the input collection that satisfy the predicate.
 *
 * 1 2 3 4-----filter{it%2==0}-----2 4
 *
 * The filter function can remove unwanted elements from a collection, but it doesn’t change the elements. Transforming elements is where map comes into play.
 * */

fun useFilter() {
    val list = listOf(1, 2, 3, 4, 5, 6)
    println(list.filter { it % 2 == 0 })
}

//map

/**
 * The map function applies given function to each element in the  collection and collects the results into a new collection.
 * The result is a new collection that contains the same number of elements
 * but each element is transformed according to the given predicate
 *
 * 1 2 3 4 5 6----map{it*2}-------2 4 6 8 10
 * */

fun useMap() {
    //ex1
    val list = listOf(1, 2, 3, 4, 5, 6)
    println(list.map { it * 2 })

    //ex2
    val people = listOf(Person("Alice", 29), Person("Bob", 31))
    val nameList = people.map { it.name }
    val nameList1 = people.map { Person::name }
    println(nameList)

    //You can easily chain several calls like that
    val result = people.filter { it.age > 30 }.map { it.name }

    //find oldest people name

    val oldestPersonName = people.filter { it.age == people.maxBy { person -> person.age }.age }

    //But note that this code repeats the process of finding the maximum age for every person,
    // so if there are 100 people in the collection, the search for the maximum age will be performed 100 times!

    val maxAge = people.maxBy { it.age }.age
    val oldPersonName = people.filter { it.age == maxAge }
}
//You can also apply the filter and transformation functions to maps:

fun useFilterAndMapInMap() {
    val numbers = mapOf(0 to "zero", 1 to "one")

    println(numbers.mapValues { it.value.uppercase() })
    println(numbers.mapKeys { it.key })
}

//5.2.2. “all”, “any”, “count”, and “find”: applying a predicate to a collection

//To demonstrate those functions,
// let’s define the predicate canBeInClub27 to check whether a person is 27 or younger:

val canBeInClub27 = { p: Person -> p.age > 27 }

fun checkPredicate() {
    val list = listOf(Person("Alice", 27), Person("John", 39), Person("Kate", 34))

    //If you’re interested in whether all the elements satisfy this predicate, you use the all function:
    println(list.all(canBeInClub27))

    //If you need to check whether there’s at least one matching element, use any:
    println(list.any(canBeInClub27))

    //If you want to know how many elements satisfy this predicate, use count:
    println(list.count(canBeInClub27))

    //To find an element that satisfies the predicate, use the find function: A synonym of find is firstOrNull,
    println(list.find(canBeInClub27))

}

//5.2.3. groupBy: converting a list to a map of groups
/**
 * Imagine that you need to divide all elements into different groups according to some quality.
 * For example, you want to group people of the same age.
 * It’s convenient to pass this quality directly as a parameter. The groupBy function can do this for you:
 * */

fun testGroupBy() {
    val list = listOf(
        Person("Alice", 27), Person("John", 34), Person("Kate", 34),
        Person("Wik", 27),
        Person("Pate", 20)
    )
    val age = { p: Person -> p.age }
    println(list.groupBy(age))

    //As another example, let’s see how to group strings by their first character using member references:
    val list1 = listOf("a", "ab", "b")
    println(list1.groupBy(String::first))
}

//5.2.4. flatMap and flatten: processing elements in nested collections

class Book(val title: String, val authors: List<String>)

/**
 *Each book was written by one or more authors. You can compute the set of all the authors in your library:
 * The flatMap function does two things:
 * At first it transforms (or maps) each element to a collection according to the function given as an argument,
 *  and then it combines (or flattens) several lists into one.
 * */
fun testFlatMap() {
    val list = listOf(Book("Effective Java", listOf("A", "B")), Book("Kotlin", listOf("C", "A")))
    println(list.flatMap { it.authors })
    println(list.flatMap { it.authors }.toSet())
}

//5.3. Lazy collection operations: sequences
/**
 * map and filter these functions create intermediate collections eagerly, meaning the intermediate
 * result of each step is stored in a temporary list.
 * Sequences give you an alternative way to perform such computations that avoids the creation of intermediate temporary object.
 * ->The Kotlin standard library reference says that both filter and map return a list.
 * ->That means this chain of calls will create two lists:
 * ->one to hold the results of the filter function and another for the results of map.
 * ->This isn’t a problem when the source list contains two elements,
 * -> but it becomes much less efficient if you have a million.
 * ->To make this more efficient,
 * you can convert the operation so it uses sequences instead of using collections directly:
 * */

fun testSeq() {
    val people = listOf(Person("Alice", 23), Person("Kate", 34), Person("Adam", 30))
    println(people.map(Person::name).filter { it.startsWith("A") })

    //using sequence

    println(people.asSequence().map(Person::name).filter { it.startsWith("A") }.toList())
}
/**
 * -> The entry point for lazy collection operations in Kotlin is the Sequence interface
 * -> The interface represents just that: a sequence of elements that can be enumerated one by one.
 * -> Sequence provides only one method, iterator,  that you can use to obtain the values from the sequence.
 * -> The strength of the Sequence interface is in the way operations on it are implemented.
 * -> The elements in a sequence are evaluated lazily.
 * -> Therefore, you can use sequences to efficiently perform chains of operations
 * on elements of a collection without creating collections to hold intermediate results of the processing.
 * -> You can convert any collection to a sequence by calling the extension function asSequence. You call toList for backward conversion.
 *
 * ->Why do you need to convert the sequence back to a collection?
 * Wouldn’t it be more convenient to use sequences instead of collections, if they’re so much better?
 *-> The answer is: sometimes. If you only need to iterate over the elements in a sequence, you can use the sequence directly.
 * -> If you need to use other API methods, such as accessing the elements by index, then you need to convert the sequence to a list.
 * */
