package com.practice.cache.utils;

import java.io.Serializable;
import java.util.Date;

public final class Data implements Serializable {

    private final String value;
    private transient Date date;

    public Data(String value) {
        this.value = value;
        this.date = new Date();
    }

    public String getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
