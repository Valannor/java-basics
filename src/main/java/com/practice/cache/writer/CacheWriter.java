package com.practice.cache.writer;

import com.practice.cache.utils.Data;

import java.io.IOException;
import java.util.Map;

public abstract class CacheWriter {

    public void write(String name, Data data) throws IOException {
        invalidateUnused();
    }

    public abstract Data read(String name);

    protected abstract Map<String, Data> invalidateUnused() throws IOException;

    public abstract boolean invalidateByName(String name) throws IOException;

    public abstract void invalidateAll();

}
