package com.practice.cache.utils;

import com.practice.cache.strategy.Strategy;

public interface CacheWriter {
    void write(String name, Data data) throws Exception;
    Data read(String name);
    void invalidateUnused(Strategy strategy) throws Exception;
    void invalidateAll();
}
