package com.datamining.classifier;

import static com.datamining.classifier.Classifier.DOCS_PER_LABEL;

public class KTooLargeException extends RuntimeException {
    public KTooLargeException() {
        super("k value cannot be larger than total of base texts available: " + (DOCS_PER_LABEL * Labels.values().length));
    }
}
