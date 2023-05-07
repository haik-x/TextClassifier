package com.datamining.classifier;
import java.util.*;

import static com.datamining.classifier.Methods.*;

public class Classifier {

    public static String classifyText(String path, Methods method, int k) {
//        switch (method) {
//            case METHOD1 ->
//        }
        return "";
    }

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

//    private static double getDistance(PriorityQueue<WordFrequency> queue1, PriorityQueue<WordFrequency> queue2, Methods methods) {
//        switch (methods) {
//            case METHOD1 ->
//        }
//    }
//
//    private static double[] method1(PriorityQueue<WordFrequency> userText) {
//
//    }



}
