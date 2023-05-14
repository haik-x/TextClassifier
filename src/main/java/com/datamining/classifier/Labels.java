package com.datamining.classifier;

import java.awt.*;

/**
 *  Labels that can be used for classification
 * **/
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
    SPORTS("Sports",2),

    /**
     * Science label
     */
    SCIENCE("Science", 3);

    private final String label;
    private final int index;


    /** Assigns values to label and index
     * @param label to save the string value of the label
     * @param index to save the index of the label
     */
    private Labels(String label, int index) {
        this.label = label; this.index=index;
    }

    /**
     * @return the String value of the label
     * **/
    public String toString() {
        return this.label;
    }

    /**
     * @return the int value of the index of the label
     * **/
    public int getIndex() {
        return index;
    }

    /**
     * Converts an index number into a Labels
     * @param index Int number to convert into a Label
     * @return The Labels that corresponds to the given index
     */
    public static Labels intoLabel(int index) {
        return switch (index) {
            case 0 -> ENTERTAINMENT;
            case 1 -> POLITICS;
            case 2 -> SPORTS;
            case 3 -> SCIENCE;
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };

    }

    /**
     * Converts a String value into a Label
     * @param labelName String  to convert into a Label
     * @return The Labels that corresponds to the given label name
     */
    public static Labels intoLabel(String labelName) {
        return switch (labelName.toLowerCase()) {
            case "entertainment" -> ENTERTAINMENT;
            case "politics" -> POLITICS;
            case "sports" -> SPORTS;
            case "science" -> SCIENCE;
            default -> throw new IllegalStateException("Unexpected value: " + labelName);
        };
    }
}

