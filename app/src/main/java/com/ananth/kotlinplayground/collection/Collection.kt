package com.ananth.kotlinplayground.collection

import java.util.*


/**
 *                         Iterable
 *                           |
 *                        Collection
 *
 *              List         Queue          Set
 *
 *              Arraylist   PriorityQueue   Hashset
 *              LinkedList
 *
 * ->Iterable interface represents objects are iterable, which means iterable or loop over with out knowing anything about details
 * ->Collection interface acts like container for other objects, it has add, remove and contains methods
 * ->List interface represents ordered collection and also called a sequence with list we can access object by index, in collection we dont care about index, we only care about adding , removing item.
 * */
fun main(){

   var list = GenericList<Int>()
   list.addItem(1)
   list.addItem(2)
   val iterator = list.iterator()
   while (iterator.hasNext()){
       val currentItem = iterator.next()
       println(currentItem)
   }

}

fun collectionDemo(){
    val collection : MutableCollection<String> = ArrayList()
//    collection.add("a")
//    collection.add("b")
//    collection.add("c")
     Collections.addAll(collection,"a","b","c")
    while (collection.iterator().hasNext()){
        println(collection.iterator().next())
    }
}