package com.practice.cache;

import com.practice.cache.utils.Data;

public interface Cache {

    void add(String name, Data data);
    void remove(String name);
    void update(String name, Data data);
}
