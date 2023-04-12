package com.ananth.kotlinplayground.collection;

import androidx.annotation.NonNull;

import java.util.Iterator;

public class GenericList1<T> implements Iterable<T>{
    private T[] items = (T[]) new Object[10];
    private int count=0;

    private void addItem(T item){
        items[count++] =item;
    }
    private T getItem(int index){
     return items[index];
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return new ListIterator(this);
    }

    private class ListIterator implements Iterator{
        private GenericList1<T> list;
        private int index=0;

        public  ListIterator(GenericList1<T> list){
            this.list = list;
        }
        @Override
        public boolean hasNext() {
            return index<list.count;
        }

        @Override
        public Object next() {
            return list.items[index++];
        }
    }
}
