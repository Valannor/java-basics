package com.practice.classloader;

import java.net.URL;

public class MyFileClassLoader extends ClassLoader{


    public Class<?> loadClass(String className, String fileLocation) throws ClassNotFoundException {
        return this.loadClass(className, false);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    @Override
    public URL getResource(String name) {
        return super.getResource(name);
    }

    @Override
    protected URL findResource(String name) {
        return super.findResource(name);
    }

}
