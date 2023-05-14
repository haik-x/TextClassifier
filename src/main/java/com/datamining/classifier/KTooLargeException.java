package com.datamining.classifier;

import static com.datamining.classifier.Classifier.BASE_TEXTS_TOTAL;

/**
 * This class extends RuntimeExeception, throw exception when k out of range.
 */

public class KTooLargeException extends RuntimeException {
    /**
     * @throws RuntimeException
     */
    public KTooLargeException() {
        super("k value cannot be larger than total of base texts available: " + BASE_TEXTS_TOTAL);
    }
}
