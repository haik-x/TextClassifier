package com.datamining.classifier;

public enum Labels {
    /**
     * Entertainment label
     */
    ENTERTAINMENT("Entertainment",0),

    /**
     * Politics label
     */
    POLITICS("Politics",1),

    /**
     * Sports label
     */
    SPORTS("Sports",2);

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

    /**
     * Converts an index number into a Labels
     * @param index Int number to convert into a Labels
     * @return The Labels that corresponds to the given index
     */
    public static Labels intoLabel(int index) {
        return switch (index) {
            case 0 -> ENTERTAINMENT;
            case 1 -> POLITICS;
            case 2 -> SPORTS;
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }
}

