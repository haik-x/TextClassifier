package com.datamining.text;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class prepares the text errasing symbols, numbers and stop words and tokenasing the text.
 */
public class Cleaning {

    public static final String [] STOP_WORDS = {"a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are",
            "arent", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can", "cannot",
            "could", "couldnt", "did", "didnt", "do", "does", "doesnt","", "doing", "dont", "down", "during", "each", "even", "ever", "few", "for", "from",
            "further", "had", "hadnt", "has", "hasnt", "have", "havent", "having", "he", "hed", "hell", "hes", "her", "here", "heres", "hers",
            "herself", "him", "himself", "his", "how", "hows", "i", "id", "im", "ive", "if", "in", "into", "is", "isnt", "it", "its",
            "itself", "lets", "me", "more", "most", "mr", "mrs", "ms", "mustnt", "my", "myself", "never", "no", "nor", "not", "of", "off", "on", "once", "only", "or", "other", "ought",
            "our", "ours", "ourselves", "out", "over", "own", "same", "shant", "she", "shed", "shell", "shes", "should", "shouldnt", "so", "some",
            "such", "than", "that", "thats", "the", "their", "theirs", "them", "themselves", "then", "there", "theres", "these", "they", "theyd",
            "theyll", "theyre", "theyve", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasnt", "we",
            "wed", "well", "were", "weve", "were", "werent", "what", "whats", "when", "whens", "where", "wheres", "which", "while", "who",
            "whos", "whom", "why", "whys", "will", "with", "wont", "would", "wouldnt", "you", "youd", "youll", "youre", "youve", "your", "yours",
            "yourself", "yourselves", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "zero", "ten"};

    /**
     * @param str The text to clean as a String
     * @return ArrayList containing each word found in the text
     */
    public static ArrayList<String> clean(String str) {
        return eraseStopWords(tokenize(cleaning(str)));
    }

    /**
     * Removes every non-letter and non-space
     * @param str The text to clean as a String
     * @return The text as a String without any special characters or number
     */
    private static String cleaning(String str) {
        return str.replaceAll("-", " ").replaceAll("\n", " ").replaceAll("[^\\w\\s]+", "").replaceAll("[^\\p{L}\\s]+", "").toLowerCase();
    }

    /**
     * Splits the text into an ArrayList containing words
     * @param str The text as a String after going through the clean method
     * @return ArrayList of the words contained in str
     */
    private static ArrayList<String> tokenize(String str) {
        Splitter splitter = Splitter.on(' ').omitEmptyStrings().trimResults();
        Iterable<String> words = splitter.split(str);
        return Lists.newArrayList(words);
    }

    /**
     * Erases stopwords from the ArrayList
     * @param str ArrayList of words obtained from the tokenize method
     * @return ArrayList of every non-stopword found in the str ArrayList
     */
    private static ArrayList<String> eraseStopWords(ArrayList<String> str) {
        List<String> stop = Arrays.asList(STOP_WORDS);
        str.removeAll(stop);
        return str;
    }
}
