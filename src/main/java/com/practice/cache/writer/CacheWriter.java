package com.practice.cache.writer;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.Strategy;

public interface CacheWriter<T> {
    void write(String name, Data data) throws Exception;
    Data read(String name);
    void invalidateUnused() throws Exception;
    void invalidateAll();
}
