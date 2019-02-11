package com.practice.cache.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HDDWriter {

    private String path;

    public HDDWriter(String path) {
        this.path = path;
    }

    public void write(String name, Data data) throws IOException {

        Path fileLocation = Paths.get(path + "/" + name);
        try (ObjectOutputStream outputStream
                     = new ObjectOutputStream(Files.newOutputStream(fileLocation))) {
            outputStream.writeObject(data);
        } catch (IOException e) {
            Files.createDirectories(Paths.get(path));
            write(name, data);
        }
    }

    public Data read(String name) {
        Data result = null;

        Path fileLocation = Paths.get(path + "/" + name);
        try (ObjectInputStream inputStream
                     = new ObjectInputStream(Files.newInputStream(fileLocation))) {
            result = (Data) inputStream.readObject();
        } catch (ClassCastException
                | ClassNotFoundException
                | IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
