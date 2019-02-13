package com.practice.cache.writer;

import com.practice.cache.utils.Data;

import java.io.IOException;
import java.util.Map;

/**
 * Base interface for cache writers. Specifies common methods.
 */
public interface CacheWriter {

    void write(String name, Data data) throws IOException;

    Data read(String name);

    Map<String, Data> invalidateUnused();

    boolean invalidateByName(String name);

    void invalidateAll();

}
