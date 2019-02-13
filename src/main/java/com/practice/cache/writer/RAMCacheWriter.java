package com.practice.cache.writer;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.Strategy;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

public class RAMCacheWriter implements CacheWriter {

    private int sizeLimit;
    private Strategy strategy;
    private LinkedHashMap<SoftReference<String>, Data> cache;

    public RAMCacheWriter(int sizeLimit, Strategy strategy) {
        this.sizeLimit = sizeLimit;
        this.strategy = strategy;

        boolean accessOrder = false;
        if (strategy == Strategy.MRU)
            accessOrder = true;
        this.cache = new LinkedHashMap<>(16, 0.75f, accessOrder);
    }

    public int getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    @Override
    public void write(String name, Data data) throws IOException {
        cache.put(new SoftReference<>(name), data);
    }

    @Override
    public Data read(String name) {
        return cache.get(new SoftReference<>(name));
    }

    @Override
    public Map<String, Data> invalidateUnused() {
        Map<String, Data> unUsed = null;
        if (cache.size() >= sizeLimit) {
            unUsed = new LinkedHashMap<>();
            int counter = sizeLimit / 3;
            for (Map.Entry<SoftReference<String>, Data> pair : cache.entrySet()) {
                SoftReference<String> key = pair.getKey();
                Data data = cache.remove(key);
                unUsed.put(key.get(), data);

                counter--;
                if (counter <= 0)
                    break;
            }
        }
        return unUsed;
    }

    @Override
    public boolean invalidateByName(String name) {
        Data data = cache.remove(new SoftReference<>(name));
        return data != null;
    }

    @Override
    public void invalidateAll() {
        cache.clear();
    }
}
