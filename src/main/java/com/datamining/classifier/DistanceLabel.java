package com.datamining.classifier;

import java.util.Comparator;

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
        if (this.distance > dl.distance) {
            return 1;
        } else if (this.distance < dl.distance) {
            return -1;
        }
        return 0;
    }
}
