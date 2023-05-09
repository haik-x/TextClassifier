package com.datamining.classifier;

public enum Labels {
    ENTERTAINMENT("Entertainment"), POLITICS("Politics"), SPORTS("Sports");

    private final String label;
    private Labels(String label) {
        this.label = label;
    }
    public String toString() {
        return this.label;
    }
}
