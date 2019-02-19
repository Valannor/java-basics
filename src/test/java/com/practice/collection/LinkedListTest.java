package com.practice.collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LinkedListTest {

    private List<String> expected;

    @Before
    public void init() {
        expected = new java.util.LinkedList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
    }

    @Test
    public void add() throws Exception {
        LinkedList<String> actual = new LinkedList<>();

        for (int i = 1; i <= 11; i++) {
            actual.add("" + i);
        }

        assertThat(actual.toString(), is(expected.toString()));
    }

    @Test
    public void add1() throws Exception {
        LinkedList<String> actual = new LinkedList<>();

        for (int i = 11; i >= 1; i--) {
            actual.add(0, "" + i);
        }

        assertThat(actual.toString(), is(expected.toString()));
    }

    @Test
    public void set() throws Exception {
        LinkedList<String> actual = new LinkedList<>();

        for (int i = 0; i <= 10; i++) {
            actual.add("NON");
            actual.set(i, "" + (i + 1));
        }

        assertThat(actual.toString(), is(expected.toString()));
    }

    @Test
    public void get() throws Exception {
        LinkedList<String> actual = new LinkedList<>();

        for (int i = 1; i <= 11; i++) {
            actual.add("" + i);
        }

        for (int i = 0; i < expected.size(); i++) {
            assertThat(actual.get(i), is(expected.get(i)));
        }
    }

    @Test
    public void remove() throws Exception {
        LinkedList<String> actual = new LinkedList<>();

        for (int i = 1; i <= 11; i++) {
            actual.add("" + i);
        }

        for (int i = 0; i < expected.size(); i++) {
            actual.remove(i);
            expected.remove(i);

            assertThat(actual.toString(), is(expected.toString()));
        }
    }

    @Test
    public void addFirst() throws Exception {
        LinkedList<String> actual = new LinkedList<>();

        for (int i = 11; i >= 1; i--) {
            actual.addFirst("" + i);
        }

        assertThat(actual.toString(), is(expected.toString()));
    }

    @Test
    public void addLast() throws Exception {
        LinkedList<String> actual = new LinkedList<>();

        for (int i = 1; i <= 11; i++) {
            actual.addLast("" + i);
        }

        assertThat(actual.toString(), is(expected.toString()));
    }

}