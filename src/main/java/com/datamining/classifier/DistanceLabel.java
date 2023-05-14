package com.datamining.classifier;

    /**
     * Represents the distance between the user text and a base text and the label of such base text
     */

public class DistanceLabel implements Comparable<DistanceLabel> {
    double distance;
    Labels label;

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

        @Override
        public String toString() {
            return String.format("%s: %f", this.label, this.distance);
        }
    }
