package com.datamining.classifier;

public class WordFrequency implements Comparable<WordFrequency> {
    public String word;
    public double frequency;

    public WordFrequency(String word, int count, int total) {
        this.word = word;
        this.frequency = count / (total * 1.0);
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
