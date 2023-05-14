package com.datamining.text;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;

public class HandlingPDF {


    public HandlingPDF(){}

    public static String extractPDFText(String path){

        String text = null;

        try{
            PDDocument document = PDDocument.load(new File(path));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);

        }catch (Exception e){
            e.printStackTrace();
        }

        return text;

    }

    /*
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
     */

}
