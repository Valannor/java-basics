package com.practice.cache.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HDDWriter {

    private String path;

    public HDDWriter(String path) {
        this.path = path;
    }

    public void write(String name, byte[] data) {

        try {
            Files.write(Paths.get(path + "/" + name), data);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // TODO: 11.02.2019 Has to return Data
    public byte[] read(String name) {

        byte[] data = null;
        try {
            Path fileLocation = Paths.get(path + "/" + name);
            data = Files.readAllBytes(fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;

    }
}
