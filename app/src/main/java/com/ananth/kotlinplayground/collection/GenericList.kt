package com.ananth.kotlinplayground.collection


 class GenericList<T>: Iterable<T>{
    private val items = arrayOfNulls<Any>(10) as Array<T?>
    private var count = 0

    fun addItem(item:T){
        items[count++] = item
    }

    fun getItem(index:Int):T?{
        return items[index]
    }

    override fun iterator(): Iterator<T> {
       return ListIterator(this)
    }

    inner class ListIterator<T>(list: GenericList<T>) : Iterator<T>{
        private var index=0
        private var list : GenericList<T> = list

        override fun hasNext(): Boolean {
          return (index<list.count)
        }

        override fun next(): T {
           return list.items[index++]!!
        }

    }
}