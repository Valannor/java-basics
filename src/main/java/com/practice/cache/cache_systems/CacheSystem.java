package com.practice.cache.cache_systems;

import com.practice.cache.utils.Data;

/**
 * Base interface for caching systems. Specifies common methods.
 */
public interface CacheSystem {
    void add(String name, Data data);

    Data read(String name);

    void remove(String name);

    void removeAll();
}
