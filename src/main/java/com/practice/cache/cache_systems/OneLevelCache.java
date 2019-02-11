package com.practice.cache.cache_systems;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.Strategy;
import com.practice.cache.writer.RAMCacheWriter;

import java.io.IOException;

public class OneLevelCache implements Cache {

    private RAMCacheWriter cacheWriter;

    public OneLevelCache(int sizeLimit, Strategy strategy) {
        this.cacheWriter = new RAMCacheWriter(sizeLimit, strategy);
    }

    @Override
    public void add(String name, Data data) {
        try {
            cacheWriter.write(name, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read(String name) {
        cacheWriter.read(name);
    }

    @Override
    public void remove(String name) {
        try {
            cacheWriter.invalidateByName(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        cacheWriter.invalidateAll();
    }
}
