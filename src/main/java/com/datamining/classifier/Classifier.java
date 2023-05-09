package com.datamining.classifier;
import java.awt.*;
import java.util.*;

import static com.datamining.text.Cleaning.clean;
import static com.datamining.text.StemText.stemText;

public class Classifier {

    public static String classifyText(String path, Methods method, int k) {
//        switch (method) {
//            case METHOD1 ->
//        }
        return "";
    }

    public static ArrayList<WordFrequency> intoArrayList(TreeMap<String, Integer> stemCountMap) {
        ArrayList<WordFrequency> frequencyArray = new ArrayList<>();
        int totalWords = stemCountMap.size();
        String[] keys = stemCountMap.keySet().toArray(new String[0]);
        Integer[] values = stemCountMap.values().toArray(new Integer[0]);

        for (int i = 0; i < 20; i++) {
            String key = keys[i];
            int value = values[i];
            if (key != null)
                frequencyArray.add(new WordFrequency(key, value, totalWords));
        }
        return frequencyArray;
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


    private static double euclidean(ArrayList<WordFrequency> userText, ArrayList<WordFrequency> baseText) {
        double distance = 0;

        for (int userTextIndex = 0; userTextIndex < userText.size(); userTextIndex++) {
            WordFrequency word = userText.get(userTextIndex);
            if (baseText.contains(word)) {
                int baseTextIndex = baseText.indexOf(word);

                distance += Math.pow((word.frequency + (userTextIndex * 0.01)) - (baseText.get(baseTextIndex).frequency + (baseTextIndex * 0.01)), 2);

            } else {
                distance++;
            }
        }
        return Math.sqrt(distance);
    }

    public static void main(String[] args) {
    }

}
