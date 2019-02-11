package com.practice.cache.utils;

import java.io.Serializable;

public class Data implements Serializable{

    private final String value;

    public Data(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        return value != null ? value.equals(data.value) : data.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Data{" +
                "value='" + value + '\'' +
                '}';
    }
}
