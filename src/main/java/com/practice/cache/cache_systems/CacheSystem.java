package com.practice.cache.cache_systems;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.Strategy;
import com.practice.cache.writer.HDDCacheWriter;
import com.practice.cache.writer.RAMCacheWriter;

import java.io.IOException;
import java.util.Map;

public class CacheSystem implements Cache {

    private HDDCacheWriter hddCacheWriter;
    private RAMCacheWriter ramCacheWriter;

    public CacheSystem(int ramLimitSize, Strategy strategy) {
        this.ramCacheWriter = new RAMCacheWriter(ramLimitSize, strategy);
    }

    public CacheSystem(int ramLimitSize, int hddLimitSize, String cacheAllocation, Strategy strategy) {
        this(ramLimitSize, strategy);
        this.hddCacheWriter = new HDDCacheWriter(cacheAllocation, hddLimitSize, strategy);
    }

    public HDDCacheWriter getHddCacheWriter() {
        return hddCacheWriter;
    }

    public void setHddCacheWriter(HDDCacheWriter hddCacheWriter) {
        this.hddCacheWriter = hddCacheWriter;
    }

    public RAMCacheWriter getRamCacheWriter() {
        return ramCacheWriter;
    }

    public void setRamCacheWriter(RAMCacheWriter ramCacheWriter) {
        this.ramCacheWriter = ramCacheWriter;
    }

    @Override
    public void add(String name, Data data) {
        try {
            if (hddCacheWriter != null) {
                hddCacheWriter.invalidateByName(name);
            }
            ramCacheWriter.write(name, data);
            Map<String, Data> unUsedRAMCache = ramCacheWriter.invalidateUnused();

            if (unUsedRAMCache != null) {
                for (Map.Entry<String, Data> pair : unUsedRAMCache.entrySet()) {
                    hddCacheWriter.write(pair.getKey(), pair.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Data read(String name) {
        Data data = ramCacheWriter.read(name);
        if (data == null && hddCacheWriter != null) {
            data = hddCacheWriter.read(name);
            if (data != null) {
                try {
                    ramCacheWriter.write(name, data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    @Override
    public void remove(String name) {
        try {
            ramCacheWriter.invalidateByName(name);
            if (hddCacheWriter != null) {
                hddCacheWriter.invalidateByName(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        ramCacheWriter.invalidateAll();
        if (hddCacheWriter != null) {
            hddCacheWriter.invalidateAll();
        }
    }
}
