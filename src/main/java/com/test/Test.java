package com.test;

import com.datamining.classifier.Classifier;
import com.datamining.classifier.Methods;
import com.datamining.classifier.TextType;

public class Test {
    public static void main(String[] args) {
        String text="C:\\Users\\samab\\Proyecto POO\\TextClassifier\\src\\main\\java\\com\\test\\test.txt";
        String science="C:\\Users\\samab\\Proyecto POO\\TextClassifier\\src\\main\\java\\com\\test\\pdf1.pdf";
        String politics="President Joe Biden signed a sweeping executive order on May 12 aimed at improving the country's cybersecurity and protecting federal government networks from cyber attacks. The order includes measures such as the establishment of cybersecurity performance goals for government contractors and the creation of a standardized playbook for responding to cyber incidents. The move comes after a series of high-profile cyber attacks on U.S. entities in recent months, including the Colonial Pipeline ransomware attack and the SolarWinds supply chain attack.";
        String label = Classifier.classifyText(text, Methods.COSINE, 7, TextType.TXT);
        String label2=Classifier.classifyText(science,Methods.EUCLIDEAN, 5, TextType.PDF);
        System.out.println(label);
        System.out.println(label2);
        Classifier c1=new Classifier(politics,3,TextType.STRING);
        System.out.println(c1.classifyText(Methods.MANHATTAN));
    }
}
