package com.datamining.classifier;

public enum Labels {
    ENTERTAINMENT("Entertainment",0), POLITICS("Politics",1), SPORTS("Sports",2);

    private final String label;
    private final int index;
    private Labels(String label, int index) {
        this.label = label; this.index=index;
    }
    public String toString() {
        return this.label;
    }

    public int getIndex() {
        return index;
    }

    public static Labels intoLabel(int index) {
        return switch (index) {
            case 0 -> ENTERTAINMENT;
            case 1 -> POLITICS;
            case 2 -> SPORTS;
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }
}

