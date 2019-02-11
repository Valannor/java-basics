package com.practice.cache.writer;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.Strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class HDDCacheWriter extends CacheWriter {

    private String path;
    private long sizeLimit;
    private Strategy strategy;

    private static Comparator LRUComparator = (Comparator<File>) (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified());
    private static Comparator MRUComparator = (Comparator<File>) (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified());

    public HDDCacheWriter(String path, long sizeLimit, Strategy strategy) {
        this.path = path;
        this.sizeLimit = sizeLimit;
        this.strategy = strategy;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(long sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
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

        super.write(name, data);
    }

    @Override
    public Data read(String name) {
        Data result = null;

        Path fileLocation = Paths.get(path + "/" + name);
        try (ObjectInputStream inputStream
                     = new ObjectInputStream(Files.newInputStream(fileLocation))) {

            result = (Data) inputStream.readObject();
            result.setDate(new Date());

            fileLocation.toFile().delete();

        } catch (ClassCastException
                | ClassNotFoundException
                | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void invalidateUnused() throws IOException {

        long currentSize = getCurrentSize();

        if (currentSize > this.sizeLimit) {

            long aboveLimit = currentSize - this.sizeLimit;

            File directory = new File(path);
            File[] files = directory.listFiles();

            if (files != null) {

                if (strategy == Strategy.LRU)
                    Arrays.sort(files, LRUComparator);
                if (strategy == Strategy.MRU)
                    Arrays.sort(files, MRUComparator);

                while (aboveLimit > 0) {
                    for (File file : files) {
                        aboveLimit -= file.length();
                        file.delete();
                    }
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
    public void invalidateByName(String name) throws IOException {
        Path fileLocation = Paths.get(path + "/" + name);
        fileLocation.toFile().delete();
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
