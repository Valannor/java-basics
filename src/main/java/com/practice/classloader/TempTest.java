package com.practice.classloader;

public class TempTest {

    public static void main(String[] args) throws ClassNotFoundException {

        String className = "src.main.resources.ConsoleHelper";

        FileClassLoader loader = new FileClassLoader();
        Class consoleHelper = Class.forName(className, true, loader);

    }

}
