package com.datamining.text;
import com.google.common.base.Splitter;

public class Tokenizer {

    public static void main(String[] args) {
        String text = HandlingPDF.extractText("/Users/benjamin/Desktop/POO/TextClassifier/src/Protocolos.pdf");
        //System.out.println(text);
        Splitter splitter = Splitter.on(',').omitEmptyStrings().trimResults();
        Iterable<String> words = splitter.split(text);
        for(String token: words){
            System.out.println(token);
        }
    }
}
