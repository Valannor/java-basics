package com.practice.cache;

import com.practice.cache.utils.Strategy;
import com.practice.cache.utils.Data;
import com.practice.cache.writer.HDDCacheWriter;
import com.practice.cache.writer.RAMCacheWriter;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        Data data = new Data("Test");

        String path = "src/main/resources/serialized";
        HDDCacheWriter writer = new HDDCacheWriter(path, 1000, Strategy.LRU);
        writer.write("Test", data);

        Data test = writer.read("Test");

        new RAMCacheWriter(1000, Strategy.LRU).write("Test", data);
    }
}
