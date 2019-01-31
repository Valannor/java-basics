package com.practice.classloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyFileClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {

        return loadClass(className, false);
    }

    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {

        Class<?> result = findClass(className);
        if (resolve) {
            resolveClass(result);
        }

        return result;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {

        byte[] classToLoad = readClassFromFile(className);

        if (classToLoad == null) throw new ClassNotFoundException();

        return defineClass(className, classToLoad, 0, classToLoad.length);
    }

    private byte[] readClassFromFile(String className) {

        try {

            String directoryName = className.replace(".", "/").concat(".class");
            Path fileLocation = Paths.get(directoryName);
            return Files.readAllBytes(fileLocation);

        } catch (IOException e) {
            System.err.println("Incorrect path");
        }

        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException {

        String className = "src.main.resources.ConsoleHelper";

        MyFileClassLoader loader = new MyFileClassLoader();
        Class consoleHelper = Class.forName(className, true, loader);

    }
}
