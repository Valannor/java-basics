package com.practice.cache;

import com.practice.cache.utils.Strategy;
import com.practice.cache.utils.Data;
import com.practice.cache.writer.HDDWriter;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        Data data = new Data("Test");

        String path = "src/main/resources/serialized";
        HDDWriter writer = new HDDWriter(path, 1000);
        writer.write("Test", data);

        Data test = writer.read("Test");
        System.out.println(test);

        writer.invalidateUnused(Strategy.LRU);
    }
}
