package com.practice.collection;

public class MyHashMap <K, V> {

    private int size;
    private int capacity;
    private double loadFactor;

    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private Pair<K,V>[] elementData;

    public MyHashMap(int capacity, double loadFactor) {
        init(capacity, loadFactor);
    }

    public MyHashMap(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(double loadFactor) {
        this(DEFAULT_CAPACITY, loadFactor);
    }

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    private void init(int capacity, double loadFactor) {
        elementData = (Pair<K,V>[]) new Object[capacity];
        this.capacity = capacity;
        this.loadFactor = loadFactor;
    }

    private static class Pair<K,V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
