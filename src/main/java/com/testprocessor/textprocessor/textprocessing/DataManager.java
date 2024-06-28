package com.testprocessor.textprocessor.textprocessing;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class DataManager {

    private Map<Integer, String> dataMap = new HashMap<>();


    public void addToMap(Integer key, String element) {
        dataMap.put(key, element);

    }

    public void removeFromMap(Integer key, String element) {
        dataMap.remove(key, element);

    }

    public void printDataMap() {
        System.out.println("DataMap contents:");
        dataMap.forEach((key, value) -> System.out.println(key + ": " + value));
    }


    public Map<Integer, String> getDataMap() {
        return dataMap;
    }

    public void updateMapEntry(Integer key, String newValue) {
        dataMap.put(key, newValue);
    }
}
