package com.practice.cache.cache_systems;

import com.practice.cache.utils.Data;

public interface Cache {
    void add(String name, Data data);
    Data read(String name);
    void remove(String name);
    void removeAll();
}
