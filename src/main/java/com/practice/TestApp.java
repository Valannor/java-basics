package com.practice;

import com.practice.collection.MyArrayList;
import com.practice.collection.MyLinkedList;

import java.util.ArrayList;

public class TestApp {

    public static void main(String[] args) {
        testLinkedList();
    }

    public static void testArrayList() {
        String[] strings = {"a", "b", "c", "d", "e"};

        MyArrayList<String> list = new MyArrayList<>();
        list.add("New 1");
        list.add("New 2");
        list.add("New 3");
        list.add("New 4");
        list.add("New 5");
        list.add("New 6");
        list.add("New 7");
        list.add("New 8");
        list.add("New 9");
        list.add("New 10");
        list.add("New 11");
        list.add("New 12");

        System.out.println(list);
        list.add(5, "New 0");
        System.out.println(list);

        System.out.println(list.size());
    }

    public static void testLinkedList() {
        MyLinkedList<String> list = new MyLinkedList<>();

        list.add("New 1");
        list.add("New 2");
        list.add("New 3");
        list.add("New 4");
        list.add("New 5");

        list.add(0, "New 0");

        list.remove(0);

        System.out.println(list);
    }

}
