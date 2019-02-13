package com.practice.cache.cache_systems;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.Strategy;
import com.practice.cache.writer.HDDCacheWriter;
import com.practice.cache.writer.RAMCacheWriter;

import java.io.IOException;
import java.util.Map;

/**
 * Flexible caching system implements CacheSystem interface. Creates ONE or TWO levels of caching, according the chosen constructor.
 * Current systems level can't be reconfigured after it was created.
 * System contains two CacheWriter classes, which provide all business logic of data storing.
 * Class provides functionality to change data storing strategy, by giving access to CacheWriters which it contains.
 * Class doesn't memorize limit size and strategy. The only way to get this information - through encapsulated CacheWriters.
 */
public class FlexibleCacheSystem implements CacheSystem {

    private HDDCacheWriter hddCacheWriter;
    private RAMCacheWriter ramCacheWriter;

    /**
     * Constructor creates ONE level caching system, which stores data in RAM memory.
     *
     * @param ramLimitSize - RAM memory limit
     * @param strategy     - Strategy of data storing (LRU or MRU)
     */
    public FlexibleCacheSystem(int ramLimitSize, Strategy strategy) {
        this.ramCacheWriter = new RAMCacheWriter(ramLimitSize, strategy);
    }

    /**
     * Constructor creates TWO level caching system, which stores data both in RAM and HDD memory.
     *
     * @param ramLimitSize    - RAM memory limit
     * @param hddLimitSize    - HDD memory limit. Max disk space for caching. Specifies in bytes.
     * @param cacheAllocation - Package, where HDDCacheWriter will store data.
     * @param strategy        - Strategy of data storing (LRU or MRU).
     */
    public FlexibleCacheSystem(int ramLimitSize, int hddLimitSize, String cacheAllocation, Strategy strategy) {
        this(ramLimitSize, strategy);
        this.hddCacheWriter = new HDDCacheWriter(cacheAllocation, hddLimitSize, strategy);
    }

    /**
     * HDDCacheWriter getter. Use it for strategy and size limit reconfiguration.
     */
    public HDDCacheWriter getHddCacheWriter() {
        return hddCacheWriter;
    }

    /**
     * RAMCacheWriter getter. Use it for strategy and size limit reconfiguration.
     */
    public RAMCacheWriter getRamCacheWriter() {
        return ramCacheWriter;
    }

    /**
     * Implementation of the add method in superinterface.
     * Checks if HDDCacheWriter was created. If it is, erases cached data with chosen name from file system (In order to prevent naming collision).
     * After removal of the cached file, RAMCacheWriter writes new data to RAM memory.
     * After writing to RAM, system checks if RAM cache achieved its limit and invalidates part of RAM cache, which wasn't in use for a long time.
     * Map with cache, removed from RAM memory writes into HDD.
     *
     * @param name - Alias of the cache data
     * @param data - Cache data
     */
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

    /**
     * Implementation of the read method in superinterface.
     * RAMCacheWriter tries to find cached data with the given name.
     * If it didn't find anything, it returns null. Or if HDDCacheWriter was created, system searches for the file with this name.
     * If file with given name was found, it relocates to the RAM memory and get returned by method.
     *
     * @param name - Alias of the cached data
     * @return - Cached data
     */
    @Override
    public Data read(String name) {
        Data data = ramCacheWriter.read(name);
        if (data == null && hddCacheWriter != null) {
            data = hddCacheWriter.read(name);
            if (data != null) {
                add(name, data);
            }
        }
        return data;
    }

    /**
     * Implementation of the remove method in superinterface.
     * Method invalidates cached data with specified alis in RAM and in HDD, if HDDCacheWriter was created.
     *
     * @param name - Alias of the cached data
     */
    @Override
    public void remove(String name) {
        ramCacheWriter.invalidateByName(name);
        if (hddCacheWriter != null) {
            hddCacheWriter.invalidateByName(name);
        }
    }

    /**
     * Implementation of the removeAll method in superinterface.
     * Method invalidates all cached data in RAM and in HDD, if HDDCacheWriter was created.
     */
    @Override
    public void removeAll() {
        ramCacheWriter.invalidateAll();
        if (hddCacheWriter != null) {
            hddCacheWriter.invalidateAll();
        }
    }
}
