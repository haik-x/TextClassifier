package com.test;

import com.datamining.classifier.Classifier;
import com.datamining.classifier.Methods;
import com.datamining.classifier.textType;

public class Test {
    public static void main(String[] args) {
        String text="test2.txt";
        String label = Classifier.classifyText(text, Methods.EUCLIDEAN, 6, textType.TXT);
        System.out.println(label);
    }
}
