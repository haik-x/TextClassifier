package com.datamining.text;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cleaning {

    public static final String [] STOP_WORDS = {"a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are",
            "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can", "cannot",
            "could", "couldn't", "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "even", "ever", "few", "for", "from",
            "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers",
            "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its",
            "itself", "let's", "me", "more", "most", "mr", "mrs", "ms", "mustn't", "my", "myself", "never", "no", "nor", "not", "of", "off", "on", "once", "only", "or", "other", "ought",
            "our", "ours", "ourselves", "out", "over", "own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so", "some",
            "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd",
            "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn't", "we",
            "we'd", "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who",
            "who's", "whom", "why", "why's", "will", "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your", "yours",
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
        return str.replaceAll("\n", " ").replaceAll("[^\\w\\s]+", "").replaceAll("[^\\p{L}\\s]+", "").toLowerCase();
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
