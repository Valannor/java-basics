package com.practice.cache;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.HDDWriter;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        Data data = new Data("Test");

        String path = "src/main/resources/serialized";
        HDDWriter writer = new HDDWriter(path);
        writer.write("Test", data);

        Data test = writer.read("Test");
        System.out.println(test);
    }
}
