package com.practice.cache.writer;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.Strategy;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

public class RAMCacheWriter extends CacheWriter {

    private int sizeLimit;
    private HDDCacheWriter hddCacheWriter;
    private LinkedHashMap<SoftReference<String>, Data> cache;

    public RAMCacheWriter(int sizeLimit, Strategy strategy) {
        this(sizeLimit, strategy, null);
    }

    public RAMCacheWriter(int sizeLimit, Strategy strategy, HDDCacheWriter hddCacheWriter) {
        this.sizeLimit = sizeLimit;
        this.hddCacheWriter = hddCacheWriter;

        boolean accessOrder = false;
        if (strategy == Strategy.MRU)
            accessOrder = true;
        this.cache = new LinkedHashMap<>(16, 0.75f, accessOrder);
    }

    @Override
    public void write(String name, Data data) throws IOException {
        cache.put(new SoftReference<>(name), data);
        super.write(name, data);
    }

    @Override
    public Data read(String name) {
        Data result = cache.get(new SoftReference<>(name));
        if (result == null && hddCacheWriter != null) {
            result = hddCacheWriter.read(name);
            if (result != null) {
                cache.put(new SoftReference<>(name), result);
            }
        }

        return result;
    }

    @Override
    protected void invalidateUnused() throws IOException {
        if (cache.size() == sizeLimit) {

            int counter = sizeLimit / 3;
            for (Map.Entry<SoftReference<String>, Data> pair : cache.entrySet()) {
                SoftReference<String> key = pair.getKey();
                Data data = cache.remove(key);

                if (hddCacheWriter != null) {
                    hddCacheWriter.write(key.get(), data);
                }
                counter--;
                if (counter <= 0)
                    break;
            }
        }
    }

    @Override
    public void invalidateAll() {
        cache.clear();
    }
}
