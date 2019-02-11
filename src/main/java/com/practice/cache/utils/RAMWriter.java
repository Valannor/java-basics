package com.practice.cache.utils;

import java.util.Map;
import java.util.WeakHashMap;

public class RAMWriter/* implements CacheWriter */{

    Map<String, Data> cache = new WeakHashMap<>();

}
