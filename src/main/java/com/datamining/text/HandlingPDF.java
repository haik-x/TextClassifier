package com.datamining.text;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;

public class HandlingPDF {

    public static void main(String[] args) {

        try{
            PDDocument document = PDDocument.load(new File("/Users/benjamin/Desktop/POO/TextClassifier/src/Protocolos.pdf"));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            System.out.println(text);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
