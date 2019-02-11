package com.practice.cache.strategy;

import java.util.Comparator;

public abstract class Strategy {

    private long sizeLimit;

    public Strategy(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public long getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(long sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

//    public abstract <E> Comparator<E> getStrategyComparator();
}
