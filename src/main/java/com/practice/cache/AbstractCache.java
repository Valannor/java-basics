package com.practice.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractCache implements Cache {

    protected static class HDDWriter {

        private String path;

        public HDDWriter(String path) {
            this.path = path;
        }

        protected void write(String name, byte[] data) {

            try {
                OutputStream outputStream = new FileOutputStream(new File(path + "/" + name));
                outputStream.write(data, 0, data.length);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        protected byte[] read(String name) {

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

    @Override
    public void add(String name, Data data) {

    }

    @Override
    public void remove(String name) {

    }

    @Override
    public void update(String name, Data data) {

    }
}
