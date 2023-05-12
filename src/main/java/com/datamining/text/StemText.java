package com.datamining.text;

import java.util.*;

import static com.datamining.text.Stemming.stem;

public class StemText {
    /**
     *
     * @param words ArrayList of stemmed words
     * @return TreeMap containing a stem and the count of appeareances. Sorted by count
     */
    public static TreeMap<String, Integer> stemText(ArrayList<String> words) {
        HashMap<String, String> wordStemMap = new HashMap<>();
        HashMap<String, Integer> stemCountMap = new HashMap<>();

        for (String word:words) {
            if (!wordStemMap.containsKey(word)) {   // The word hasn't appeared already
                wordStemMap.put(word, stem(word));

                String stemKey = wordStemMap.get(word);

                if (!stemCountMap.containsKey(stemKey)) // The stem hasn't appeared in the second map
                    stemCountMap.put(stemKey, 1);
                else
                    stemCountMap.replace(stemKey, stemCountMap.get(stemKey) + 1);
            } else {
                String stemKey = wordStemMap.get(word);
                stemCountMap.replace(stemKey, stemCountMap.get(stemKey) + 1);
            }
        }
        return valueSort(stemCountMap);
    }

    private static <String, Integer extends Comparable<Integer>> TreeMap<String, Integer> valueSort(Map<String, Integer> map) {
        /*
            Override compare method so that TreeMap is ordered by value instead of key
         */
        Comparator<String> valueComparator = new Comparator<String>() {
            @Override
            public int compare(String word1, String word2) {
                int comp = map.get(word2).compareTo(map.get(word1));
                if (comp == 0)
                    return 1;
                else
                    return comp;
            }
        };
        TreeMap<String, Integer> sorted = new TreeMap<>(valueComparator);
        sorted.putAll(map);
        return sorted;
    }
}
