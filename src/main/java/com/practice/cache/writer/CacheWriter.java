package com.practice.cache.writer;

import com.practice.cache.utils.Data;

import java.io.IOException;

public abstract class CacheWriter {

    public void write(String name, Data data) throws IOException {
        invalidateUnused();
    }

    public abstract Data read(String name);

    protected abstract void invalidateUnused() throws IOException;

    public abstract void invalidateByName(String name) throws IOException;

    public abstract void invalidateAll();

}
