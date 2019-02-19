package com.practice.cache;

import com.practice.cache.utils.Strategy;
import com.practice.cache.utils.Data;
import com.practice.cache.writer.HDDCacheWriter;
import com.practice.cache.writer.RAMCacheWriter;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws IOException {
        Data data = new Data("Test");

        String path = "src/main/resources/serialized";
        HDDCacheWriter writer = new HDDCacheWriter(path, 1000, Strategy.LRU);
        writer.write("Test", data);

        Data test = writer.read("Test");

        RAMCacheWriter ramCacheWriter = new RAMCacheWriter(1000, Strategy.LRU);
        ramCacheWriter.write("Test", data);
        for (Map.Entry<String, SoftReference<Data>> pair : ramCacheWriter.getCache().entrySet()) {
            pair.getKey();
        }

        System.out.println(ramCacheWriter.getCache());
    }
}
