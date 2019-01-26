package com.practice.collection;

public class TestApp {

    public static void main(String[] args) {

//        testArrayList();
        testLinkedList();
//        testHashMap();
    }

    private static void testHashMap() {

        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("Key 0", "Value 0");
        map.put("Key 1", "Value 1");
        map.put("Key 2", "Value 2");
        map.put("Key 3", "Value 3");
        map.put("Key 4", "Value 4");
        map.put(null, "Value 4");
        System.out.println("Map after initialisation: " + map);

        map.put("Key 0", "Value 9");
        System.out.println("Map after second put for key \"Key 0\" : " + map);

        System.out.println("Map size: " + map.size());

        System.out.println("Get for not existing key \"Key\": " + map.get("Key"));
        System.out.println("Get for existing key \"Key 0\": " + map.get("Key 0"));

        System.out.println("Return of removal result for not existing key \"Key\": " + map.remove("Key"));
        System.out.println("Return of removal result for existing key \"Key 0\": " + map.remove("Key 0"));

        System.out.println("Map after all operations: " + map);
        System.out.println("Map size after all operations: " + map.size());
    }

    public static void testArrayList() {

        MyArrayList<String> list = new MyArrayList<>();
        list.add("New 1");
        list.add("New 2");
        list.add("New 3");
        list.add("New 4");
        list.add("New 5");
        list.add("New 6");
        list.add("New 7");
        list.add("New 8");
        list.add("New 9");
        list.add("New 10");
        System.out.println("10 elements added: " + list);

        list.add("New 11");
        list.add("New 12");
        System.out.println("2 more elements added (for list capacity check): " + list);

        System.out.println("List size: " + list.size());

        System.out.println("Get by index 10: " + list.get(10));

        list.add(5, "New 0");
        System.out.println("After adding \"New 0\" in the middle of the list (index 5): " + list);
        System.out.println("List size: " + list.size());

        System.out.println("Removing element located in index 5 position: " + list.remove(5));
        System.out.println("Removing element \"New 12\" position: " + list.remove("New 12"));
        System.out.println("Removing not existing element \"Newwww\": " + list.remove("Newwww"));
        System.out.println("After remove operations: " + list);

        list.set(10, "Old");
        System.out.println("Set value \"Old\" in index 10 (contains \"New 11\"): " + list);


        String[] strings = {"a", "b", "c", "d", "e"};
        System.out.println("Creating list using array: " + new MyArrayList<>(strings));
    }

    public static void testLinkedList() {
        MyLinkedList<String> list = new MyLinkedList<>();

        list.add("New 1");
        list.add("New 2");
        list.add("New 3");
        list.add("New 4");
        list.add("New 5");
        System.out.println("5 elements added to list: " + list);

        list.add(0, "New 0");
        System.out.println("\"New 0\" added in position 0: " + list);

        list.remove(0);
        System.out.println("Removing element by index 0: " + list);

        list.addLast("Last");
        list.addFirst("First");
        System.out.println("addFirst and addLast methods used: " + list);
    }

}
