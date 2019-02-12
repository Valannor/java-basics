package com.practice.cache.writer;

import com.practice.cache.utils.Data;

import java.io.IOException;
import java.util.Map;

public interface CacheWriter {

    void write(String name, Data data) throws IOException;

    Data read(String name);

    Map<String, Data> invalidateUnused() throws IOException;

    boolean invalidateByName(String name) throws IOException;

    void invalidateAll();

}
