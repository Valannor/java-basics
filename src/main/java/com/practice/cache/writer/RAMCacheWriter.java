package com.practice.cache.writer;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.Strategy;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class implements CacheWriter interface.
 * Provides functionality for read-write-remove operations with cached data in RAM.
 * Strategy of the data storing relies on encapsulated comparators.
 * Limit size property of the writer can be reconfigured.
 */
public class RAMCacheWriter implements CacheWriter {

    /**
     * Main properties of the writer.
     */
    private int sizeLimit;
    private Strategy strategy;

    /**
     * Cached data saves in LinkedHashMap.
     * Value stores in SoftReference type.
     */
    private LinkedHashMap<String, SoftReference<Data>> cache;

    /**
     * Constructor for RAMCacheWriter.
     * Sets strategy and size limit for cache keeper.
     * Initializes cache keeper with LinkedHashMap.
     *
     * @param sizeLimit - RAM memory limit. Max space for caching. Specifies in number of cached entities.
     * @param strategy  - Strategy of data storing (LRU or MRU).
     */
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

    public LinkedHashMap<String, SoftReference<Data>> getCache() {
        return cache;
    }

    /**
     * Implementation of the write method in superinterface.
     * Method puts cache with specified name inside the LinkedHashMap.
     * Value is stored in SoftReference in order to prevent OutOfMemory, which could cause by cache.
     *
     * @param name - Alias of the cache data
     * @param data - Cache data
     * @throws IOException - Happens if creation of directory fails
     */
    @Override
    public void write(String name, Data data) throws IOException {
        cache.put(name, new SoftReference<>(data));
    }

    /**
     * Implementation of the read method in superinterface.
     * Method gets SoftReference representation of Data from Map. Then, it return Data from reference object.
     *
     * @param name - Alias of the cache data
     * @return - Cached data
     */
    @Override
    public Data read(String name) {
        return cache.get(name).get();
    }

    /**
     * Implementation of the invalidateUnused method in superinterface.
     * If cache size reached sizeLimit, method removes objects from cache-map and stores them in map to return.
     * If somehow data stored in RAM appears to have null value, we don't pass it through map to return.
     *
     * @return - In this implementation it returns map of values
     */
    @Override
    public Map<String, Data> invalidateUnused() {
        Map<String, Data> unUsed = null;
        if (cache.size() >= sizeLimit) {
            unUsed = new LinkedHashMap<>();
            int counter = sizeLimit / 3;
            for (Map.Entry<String, SoftReference<Data>> pair : cache.entrySet()) {
                String key = pair.getKey();
                Data data = cache.remove(key).get();

                if (data != null) {
                    unUsed.put(key, data);
                }

                counter--;
                if (counter <= 0)
                    break;
            }
        }
        return unUsed;
    }

    /**
     * Implementation of the invalidateByName method in superinterface.
     * Method removes cached data from RAM.
     * If file was erased, method return true, else-way it signals, that there is no data by provided name in cache.
     *
     * @param name - Alias of the cache data
     * @return - Boolean result of file deletion
     */
    @Override
    public boolean invalidateByName(String name) {
        Data data = cache.remove(name).get();
        return data != null;
    }

    /**
     * Implementation of the invalidateAll method in superinterface.
     * Method simply clears the map.
     */
    @Override
    public void invalidateAll() {
        cache.clear();
    }
}
