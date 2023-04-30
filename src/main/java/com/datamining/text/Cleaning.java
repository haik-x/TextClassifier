package com.datamining.text;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cleaning {

    public static final String [] STOP_WORDS ={"a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are",
            "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can", "cannot",
            "could", "couldn't", "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "few", "for", "from",
            "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers",
            "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its",
            "itself", "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "or", "other", "ought",
            "our", "ours", "ourselves", "out", "over", "own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so", "some",
            "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd",
            "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn't", "we",
            "we'd", "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who",
            "who's", "whom", "why", "why's", "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your", "yours",
            "yourself", "yourselves"};

    public static String Cleaning(String str){
        String str2=str.replaceAll("[^\\w\\s]+", "").replaceAll("[^\\p{L}\\s]+", "").toLowerCase();
        return str2;
    }
    public  static ArrayList<String> Tokenize(String str){
        Splitter splitter = Splitter.on(' ').omitEmptyStrings().trimResults();
        Iterable<String> words = splitter.split(str);
        return Lists.newArrayList(words);
    }

    public static ArrayList<String> eraseStopWords(ArrayList<String> str){
        List<String> stop =Arrays.asList(STOP_WORDS);
       boolean str2=str.removeAll(stop);

        return str;
    }

    public static void main(String[] args) {
        String str="The quick brown fox j$umped %over the lazy dog. The dog's ba/7rked loudly in surprise, but the fox was already gone, disappearing into the nearby woods.";


        System.out.println(eraseStopWords(Tokenize(Cleaning(str))));
    }
}
