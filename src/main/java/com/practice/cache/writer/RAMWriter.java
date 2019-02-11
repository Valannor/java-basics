package com.practice.cache.writer;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.Strategy;

import java.util.Map;
import java.util.WeakHashMap;

public class RAMWriter implements CacheWriter {

    Map<String, Data> cache = new WeakHashMap<>();

    @Override
    public void write(String name, Data data) throws Exception {

    }

    @Override
    public Data read(String name) {
        return null;
    }

    @Override
    public void invalidateUnused() throws Exception {

    }

    @Override
    public void invalidateAll() {

    }
}
