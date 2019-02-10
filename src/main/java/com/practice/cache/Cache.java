package com.practice.cache;

public interface Cache {

    void add(String name, Data data);
    void remove(String name);
    void update(String name, Data data);
}
