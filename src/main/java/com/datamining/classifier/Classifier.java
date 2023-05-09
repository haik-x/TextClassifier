package com.datamining.classifier;
import com.datamining.text.Word;

import java.util.*;

import static com.datamining.classifier.Methods.*;
import static com.datamining.text.Cleaning.clean;
import static com.datamining.text.StemText.stemText;

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


    private static double euclidean(PriorityQueue<WordFrequency> newDoc, PriorityQueue<WordFrequency> baseDoc) {
        double distance = 0;
        int newDocIndex = 0;
        ArrayList<WordFrequency> baseDocList = new ArrayList<>(baseDoc);

        for (WordFrequency word: newDoc) {
            if (baseDocList.contains(word)) {
                int baseDocIndex = baseDocList.indexOf(word);

                distance += Math.pow(newDoc.poll().frequency - baseDocList.get(baseDocIndex).frequency, 2) +
                            Math.pow(newDocIndex - baseDocIndex, 2);

            } else {
                newDoc.poll();
                distance++;
            }
            newDocIndex++;
        }
        return distance;
    }

    public static void main(String[] args) {
        System.out.println(euclidean(intoQueue(stemText(clean("The world of politics is constantly in flux, with shifting alliances, rising tensions, and changing policies all contributing to a dynamic and often tumultuous landscape. In the United States, partisan politics have reached new heights, with both major political parties entrenched in their positions and little room for compromise or collaboration. Issues like healthcare, immigration, gun control, and climate change continue to dominate the national conversation, with each side fiercely advocating for their preferred solutions. Internationally, relationships between countries are strained, with trade wars, economic sanctions, and military conflicts threatening to disrupt global stability. Tensions between the United States and countries like China, Russia, and North Korea have reached unprecedented levels, with each side engaging in a high-stakes game of brinksmanship. The ongoing crisis in the Middle East has led to displacement, violence, and humanitarian crises, with millions of people forced to flee their homes and seek refuge in other countries. At the same time, new technologies are changing the nature of political discourse, with social media and other digital platforms providing new opportunities for engagement, mobilization, and advocacy. However, these same technologies have also created new challenges, such as the spread of disinformation and the erosion of privacy and security.  Despite these challenges, there are reasons for optimism in the political sphere. Grassroots movements and social justice campaigns are gaining momentum, with activists advocating for greater representation, inclusivity, and accountability in government. At the same time, innovative policy proposals are emerging that seek to address longstanding issues like inequality, poverty, and climate change. As we navigate this complex and ever-changing political landscape, it's important to stay informed, engaged, and committed to the principles and values that underpin democracy and good governance. By working together, we can create a brighter and more just future for ourselves and for generations to come."))), intoQueue(stemText(clean("The world of politics is constantly in flux, with shifting alliances, rising tensions, and changing policies all contributing to a dynamic and often tumultuous landscape. In the United States, partisan politics have reached new heights, with both major political parties entrenched in their positions and little room for compromise or collaboration. Issues like healthcare, immigration, gun control, and climate change continue to dominate the national conversation, with each side fiercely advocating for their preferred solutions. Internationally, relationships between countries are strained, with trade wars, economic sanctions, and military conflicts threatening to disrupt global stability. Tensions between the United States and countries like China, Russia, and North Korea have reached unprecedented levels, with each side engaging in a high-stakes game of brinksmanship. The ongoing crisis in the Middle East has led to displacement, violence, and humanitarian crises, with millions of people forced to flee their homes and seek refuge in other countries. At the same time, new technologies are changing the nature of political discourse, with social media and other digital platforms providing new opportunities for engagement, mobilization, and advocacy. However, these same technologies have also created new challenges, such as the spread of disinformation and the erosion of privacy and security.  Despite these challenges, there are reasons for optimism in the political sphere. Grassroots movements and social justice campaigns are gaining momentum, with activists advocating for greater representation, inclusivity, and accountability in government. At the same time, innovative policy proposals are emerging that seek to address longstanding issues like inequality, poverty, and climate change. As we navigate this complex and ever-changing political landscape, it's important to stay informed, engaged, and committed to the principles and values that underpin democracy and good governance. By working together, we can create a brighter and more just future for ourselves and for generations to come.")))));
    }

}
