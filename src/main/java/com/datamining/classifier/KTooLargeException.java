package com.datamining.classifier;

import static com.datamining.classifier.Classifier.BASE_TEXTS_TOTAL;

/**
 * This class extends RuntimeExeception, throw exception when k is larger than the amount base texts.
 */

public class KTooLargeException extends RuntimeException {
    /**
     * @throws RuntimeException when k value is larger than the amount base texts.
     */
    public KTooLargeException() {
        super("k value cannot be larger than total of base texts available: " + BASE_TEXTS_TOTAL);
    }
}
