package com.practice.cache.cache_systems;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.Strategy;
import com.practice.cache.writer.HDDCacheWriter;
import com.practice.cache.writer.RAMCacheWriter;

import java.io.IOException;

public class TwoLevelCache implements Cache {

    private RAMCacheWriter ramCacheWriter;
    private HDDCacheWriter hddCacheWriter;

    public TwoLevelCache(int levelOneLimit, int levelTwoLimit, String cacheAllocation, Strategy strategy) {
        this.hddCacheWriter = new HDDCacheWriter(cacheAllocation, levelTwoLimit, strategy);
        this.ramCacheWriter = new RAMCacheWriter(levelOneLimit, strategy, hddCacheWriter);
    }

    @Override
    public void add(String name, Data data) {
        try {
            ramCacheWriter.write(name, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read(String name) {
        ramCacheWriter.read(name);
    }

    @Override
    public void remove(String name) {
        try {
            ramCacheWriter.invalidateByName(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        ramCacheWriter.invalidateAll();
    }
}
