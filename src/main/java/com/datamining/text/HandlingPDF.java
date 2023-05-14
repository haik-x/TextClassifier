package com.datamining.text;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;

/**
 *  This class extracts the text from a .pdf file and passes it into a String.
 */

public class HandlingPDF {


    public HandlingPDF(){}

    /**
     * Extract PDF text.
     * @param path path of the file
     * @return a String of the PDF text.
     */

    public static String extractPDFText(String path){
        // null to avoid process continue without a text if no path find.
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


}
