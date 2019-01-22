package com.practice.collection;

import java.util.Arrays;
import java.util.Iterator;

public class MyArrayList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private E[] elements;

    private int capacity;
    private int size;

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

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

    public void add(E element) {
        ensureCapacity();
        elements[size] = element;
        size++;
    }

    public void add(int index, E element) {

        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        if (index == size) {
            add(element);
            return;
        }

        // TODO: 22.01.2019 Need to improve this part
        ensureCapacity();
        E[] temp = (E[]) new Object[capacity];
        System.arraycopy(elements, 0, temp, 0, index);
        temp[index] = element;
        System.arraycopy(elements, index, temp, index + 1, elements.length - (index + 1));

        elements = temp;
        size++;
    }

    private void ensureCapacity() {
        if (size >= capacity) {
            capacity = (capacity * 3) / 2 + 1;

            elements = Arrays.copyOf(elements, capacity);
        }
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("[");
        for (int i = 0; i < size; i++) {

            builder.append(elements[i]);

            if (i < size - 1) {
                builder.append(", ");
            } else
                builder.append("]");
        }

        return builder.toString();
    }

    public int size() {
        return size;
    }
}
