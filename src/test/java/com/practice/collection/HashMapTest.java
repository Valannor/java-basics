package com.practice.collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class HashMapTest {

    private Map <String, String> expected;
    private HashMap <String, String> actual;

    @Before
    public void init() {
        expected = new java.util.HashMap<>();
        expected.put(null, "Value 4");
        expected.put("Key 0", "Value 0");
        expected.put("Key 1", "Value 1");
        expected.put("Key 2", "Value 2");
        expected.put("Key 3", "Value 3");
        expected.put("Key 4", "Value 4");

        actual = new HashMap<>();
        actual.put("Key 0", "Value 0");
        actual.put("Key 1", "Value 1");
        actual.put("Key 2", "Value 2");
        actual.put("Key 3", "Value 3");
        actual.put("Key 4", "Value 4");
        actual.put(null, "Value 4");
    }

    @Test
    public void put() throws Exception {
        assertThat(actual.toString(), is(expected.toString()));

        expected.put("Key 0", "Value 9");
        actual.put("Key 0", "Value 9");
        assertThat(actual.toString(), is(expected.toString()));

    }

    @Test
    public void remove() throws Exception {
        assertThat(actual.remove("Key") == null, is(true));
        assertThat(actual.remove("Key 0"), is("Value 0"));
    }

    @Test
    public void get() throws Exception {
        assertThat(actual.get("Key") == null, is(true));
        assertThat(actual.get("Key 0"), is("Value 0"));
    }

}