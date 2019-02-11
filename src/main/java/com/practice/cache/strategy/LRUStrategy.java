package com.practice.cache.strategy;

import java.util.Comparator;

public class LRUStrategy extends Strategy{

    public LRUStrategy(int sizeLimit) {
        super(sizeLimit);
    }

}
