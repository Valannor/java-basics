package com.practice.cache.writer;

import com.practice.cache.utils.Data;
import com.practice.cache.utils.Strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/**
 * Class implements CacheWriter interface.
 * Provides functionality for read-write-remove operations with cached data in HDD.
 * Strategy of the data storing relies on encapsulated comparators.
 * Properties of the writer can easily be reconfigured (including directory, limit size and strategy).
 */
public class HDDCacheWriter implements CacheWriter {

    /**
     * Main properties of the writer.
     */
    private String path;
    private long sizeLimit;
    private Strategy strategy;

    /**
     * Current cache size counter.
     */
    private long currentSize;

    /**
     * Comparators for each strategy.
     */
    private static Comparator LRUComparator = (Comparator<File>) (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified());
    private static Comparator MRUComparator = (Comparator<File>) (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified());

    /**
     * Constructor for HDDCacheWriter.
     *
     * @param path      - Package, where HDDCacheWriter will store data.
     * @param sizeLimit - HDD memory limit. Max disk space for caching. Specifies in bytes.
     * @param strategy  - Strategy of data storing (LRU or MRU).
     */
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

    /**
     * Implementation of the write method in superinterface.
     * Method creates output stream for path, specified by combination of class field 'path' with 'name' parameter.
     * IOException in this step can mean an absence of directory equal to 'path'.
     * At this point, we create directory and launch write method again.
     * Then, in catch block, we return from method to prevent extra incrementation of the counter.
     * After successful write we increment counter (class filed) 'currentSize' with length of the file (provided in bytes).
     * In the last step we invoke invalidateUnused method to erase outdated cache.
     *
     * @param name - Alias of the cache data
     * @param data - Cache data
     * @throws IOException - Happens if creation of directory fails
     */
    @Override
    public void write(String name, Data data) throws IOException {
        Path fileLocation = getPath(name);
        try (ObjectOutputStream outputStream
                     = new ObjectOutputStream(Files.newOutputStream(fileLocation))) {
            outputStream.writeObject(data);
        } catch (IOException e) {
            Files.createDirectories(Paths.get(path));
            write(name, data);
            return;
        }
        currentSize += fileLocation.toFile().length();
        invalidateUnused();
    }

    /**
     * Serving method which returns instance of java.nio.file.Path by combining field 'path' with 'name' parameter.
     *
     * @param name - Alias of the cache data
     * @return - java.nio.file.Path instance
     */
    private Path getPath(String name) {
        return Paths.get(path + "/" + name);
    }

    /**
     * Implementation of the read method in superinterface.
     * Method creates input stream for path, specified by combination of class field 'path' with 'name' parameter.
     * If the file which was read by system is valid, method deletes it from HDD and returns as a result.
     *
     * @param name - Alias of the cache data
     * @return - Cached data
     */
    @Override
    public Data read(String name) {
        Data result = null;

        Path fileLocation = getPath(name);
        try (ObjectInputStream inputStream
                     = new ObjectInputStream(Files.newInputStream(fileLocation))) {

            result = (Data) inputStream.readObject();
            fileLocation.toFile().delete();

        } catch (ClassCastException
                | ClassNotFoundException
                | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Implementation of the invalidateUnused method in superinterface.
     * If currentSize counter reached sizeLimit, method locates directory with cached data and creates an array of java.io.File objects.
     * Array checks for null and get sorted according Strategy, specified for current writer.
     * After this system removes cache files, until currentSize counter is less than half of the sizeLimit.
     *
     * @return - In this implementation it always returns null
     */
    @Override
    public Map<String, Data> invalidateUnused() {
        if (currentSize >= this.sizeLimit) {

            File directory = new File(path);
            File[] files = directory.listFiles();

            if (files != null) {

                if (strategy == Strategy.LRU)
                    Arrays.sort(files, LRUComparator);
                if (strategy == Strategy.MRU)
                    Arrays.sort(files, MRUComparator);

                while (currentSize > sizeLimit / 2) {
                    for (File file : files) {
                        currentSize -= file.length();
                        file.delete();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Method added in order to recount currentSize if needed.
     *
     * @return - HDD space which was taken by cache (measured in bytes)
     * @throws IOException - Thrown if directory is absent
     */
    public long ensureCurrentSize() throws IOException {
        Path directory = Paths.get(path);
        return Files.walk(directory)
                .filter(p -> p.toFile().isFile())
                .mapToLong(p -> p.toFile().length())
                .sum();
    }

    /**
     * Implementation of the invalidateByName method in superinterface.
     * Method locates file by name and deletes it from HDD.
     * If file was erased, method return true, else-way result is false.
     *
     * @param name - Alias of the cache data
     * @return - Boolean result of file deletion
     */
    @Override
    public boolean invalidateByName(String name) {
        Path fileLocation = getPath(name);
        return fileLocation.toFile().delete();
    }

    /**
     * Implementation of the invalidateAll method in superinterface.
     * Method locates cache directory by path, builds an array of cache files and deletes them all.
     */
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
