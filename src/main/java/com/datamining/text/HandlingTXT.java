package com.datamining.text;



import java.io.File;
import java.io.IOException;

import java.util.Scanner;


public class HandlingTXT {

    public static String extractTXTText(String path){
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(path).getAbsoluteFile();
            Scanner Reader = new Scanner(file);

            while (Reader.hasNextLine()) {
                data.append(Reader.nextLine()).append(" ");
            }

            Reader.close();
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        return data.toString();
    }
}
