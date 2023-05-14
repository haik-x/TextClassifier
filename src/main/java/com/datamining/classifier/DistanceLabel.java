package com.datamining.classifier;

    /**
     * Represents the distance between the user text and a base text and the label of such base text
     */

public class DistanceLabel implements Comparable<DistanceLabel> {
    /**
     * The representation of the distance between a base text and a user text.
     */
    double distance;
    /**
     * The label of the base text which its distance was obtained.
     */
    Labels label;

    /**
     * Constructor of the object Distance lable
     * @param distance distance between a base text and a user text.
     * @param label label of the base text
     */
    public DistanceLabel(double distance, Labels label) {
        this.distance = distance;
        this.label = label;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DistanceLabel dl) {
            return this.distance == dl.distance;
        }
        return false;
    }

    /**
     * @Override 'compareTo' function, compares distances and orders them from the smallest to the largest distance.
     * @param dl the object to be compared.
     * @return 0 if equals, 1 if this larger and -1 if this smaller.
     */

    @Override
    public int compareTo(DistanceLabel dl) {
        if(Math.abs(this.distance-dl.distance)<0.0000001)
            return 0;
        else if (this.distance > dl.distance) {
            return 1;
        } else {
            return -1;
        }
    }
        /**
         * @return toString of DistanceLabel object.
         */

    @Override
    public String toString() {
        return String.format("%s: %f", this.label, this.distance);
    }
}
