package com.test;

import com.datamining.classifier.Classifier;
import com.datamining.classifier.Methods;
import com.datamining.classifier.TextType;

public class Test {
    public static void main(String[] args) {
        String text="C:\\Users\\samab\\Proyecto POO\\TextClassifier\\src\\main\\java\\com\\test\\test.txt";
        String label = Classifier.classifyText(text, Methods.COSINE, 7, TextType.TXT);
        
        System.out.println(label);
    }
}
