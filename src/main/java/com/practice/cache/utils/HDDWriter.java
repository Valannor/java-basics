package com.practice.cache.utils;

import com.practice.cache.strategy.Strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class HDDWriter implements CacheWriter {

    private String path;

    public HDDWriter(String path) {
        this.path = path;
    }

    @Override
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

    @Override
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

    @Override
    public void invalidateUnused(Strategy strategy) throws IOException {

        long currentSize = getCurrentSize();

        if (currentSize > strategy.getSizeLimit()) {

            long aboveLimit = currentSize - strategy.getSizeLimit();

            File directory = new File(path);
            File[] files = directory.listFiles();

            if (files != null) {
                Arrays.sort(files, strategy.getStrategyComparator());
            }

            while (aboveLimit > 0) {
                for (File file : files) {
                    aboveLimit -= file.length();
                    file.delete();
                }
            }
        }
    }

    private long getCurrentSize() throws IOException {
        Path directory = Paths.get(path);
        return Files.walk(directory)
                .filter(p -> p.toFile().isFile())
                .mapToLong(p -> p.toFile().length())
                .sum();
    }

    @Override
    public void invalidateAll() {
        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}
