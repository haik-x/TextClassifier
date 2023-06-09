package com.datamining.classifier;

/**
 * Represents a stemmed word and its frequency of appereance in the text it comes from
 */
public class WordFrequency implements Comparable<WordFrequency> {
    public String word;
    public double frequency;

    /**
     * @param word Stemmed word
     * @param count Number of appereances of the stemmed word in the text
     * @param total Total of stems in the text
     */
    public WordFrequency(String word, int count, int total) {
        this.word = word;
        this.frequency = count / (total * 1.0);
    }

    /**
     * @param word Stemmed word
     * @param frequency Frequency of appereance in the text
     */
    public WordFrequency(String word, double frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(WordFrequency wf) {
        if (this.frequency < wf.frequency)
            return 1;
        else if (this.frequency > wf.frequency)
            return -1;
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WordFrequency wf)
            return this.word.equals(wf.word);
        return false;
    }

    @Override
    public String toString() {
        return this.word + "=" + this.frequency;
    }
}
