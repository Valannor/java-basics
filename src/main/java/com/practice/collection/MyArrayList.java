package com.practice.collection;

import java.util.Iterator;

public class MyArrayList<E> implements Iterable{

    private E[] elements;

    private int capacity;
    private int size;

    public MyArrayList(int capacity) {
        this.capacity = capacity;
        elements = (E[]) new Object[capacity];
    }

    public MyArrayList(E[] source) {
        this.elements = source;

        capacity = (source.length * 3) / 2 + 1;
        size = source.length;
        System.arraycopy(source, 0, this.elements, 0, source.length);
    }


    // TODO: 22.01.2019
    @Override
    public Iterator iterator() {

        return null;
    }


}
