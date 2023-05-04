package com.datamining.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static com.datamining.text.Stemming.stem;

public class StemText {
    public static HashMap<String, Integer> stemText(ArrayList<String> words) {
        /*
            Given a list of words, create two maps to store <word, stem> and <stem, count>
            The first stores non-repeated words and their stem, while the second counts
            how many times a stem appears
         */

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
        return stemCountMap;
    }

    public static void main(String[] args) {
        ArrayList<String> test = new ArrayList<>();
        Collections.addAll(test, "Accidentally", "Accident", "Accidentally",  "Exaggeration", "Indefatigable", "Ineffable", "Inevitability", "Irrevocable", "Magnanimous", "Multifarious", "Perpetuity", "Philanthropic", "Propensity", "Quintessence", "Remonstrance", "Serendipitous", "Supercilious", "Transcendental", "Ubiquitous", "Vociferous", "Consistent", "Consisting", "Consistency", "Consists");
        System.out.println(test);
        System.out.println(stemText(test));
    }
}
