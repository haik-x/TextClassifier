package com.datamining.classifier;

import static com.datamining.classifier.Classifier.BASE_TEXTS_TOTAL;

public class KTooLargeException extends RuntimeException {
    public KTooLargeException() {
        super("k value cannot be larger than total of base texts available: " + BASE_TEXTS_TOTAL);
    }
}
