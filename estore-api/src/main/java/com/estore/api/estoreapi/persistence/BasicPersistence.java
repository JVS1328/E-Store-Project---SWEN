package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BasicPersistence<T extends Identified> {
    public Map<Integer, T> data;
    public ObjectMapper objectMapper;
    public String filename;
    public static int nextId;

    public boolean load(Class<T> type) throws IOException {
        data = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of T
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        T[] dataArray = objectMapper.readValue(new File(filename), objectMapper.constructType(type.arrayType()));

        // Add each user to the tree map and keep track of the greatest id
        for (T obj : dataArray) {
            data.put(obj.getId(), obj);
            if (obj.getId() > nextId)
                nextId = obj.getId();
        }
        
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
     * Generates the next id for a new {@linkplain Product product}
     * 
     * @return The next id
     */
    public synchronized static int nextId() {
        return nextId++;
    }
}