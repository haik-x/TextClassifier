package com.datamining.classifier;

import java.util.*;

public class Classifier {

    public static PriorityQueue<WordFrequency> intoQueue(TreeMap<String, Integer> stemCountMap) {
        PriorityQueue<WordFrequency> frequencyQueue = new PriorityQueue<>();
        int totalWords = stemCountMap.size();
        String[] keys = stemCountMap.keySet().toArray(new String[0]);
        Integer[] values = stemCountMap.values().toArray(new Integer[0]);

        for (int i = 0; i < 20; i++) {
            String key = keys[i];
            int value = values[i];
            if (key != null)
                frequencyQueue.offer(new WordFrequency(key, value, totalWords));
        }
        return frequencyQueue;
    }
}
