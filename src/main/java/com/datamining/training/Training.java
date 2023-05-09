package com.datamining.training;

import com.datamining.classifier.Labels;
import com.datamining.classifier.WordFrequency;
import static com.datamining.classifier.Classifier.intoArrayList;
import static com.datamining.text.Cleaning.clean;
import static com.datamining.text.StemText.stemText;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Training {
    public static final int docsPerLabel = 3;
    private static void createArrays() {
        for (Labels label: Labels.values()) {
            File arraysFile;
            FileWriter writer;

            try {
                arraysFile = new File("dataset\\Label\\" + label + "\\" + label.toString().toLowerCase() + "Arrays.txt").getAbsoluteFile();
                writer = new FileWriter(arraysFile);
                writer.write("");
                writer = new FileWriter(arraysFile, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (int i = 1; i <= docsPerLabel; i++) {
                try {
                    File file = new File("dataset\\Label\\" + label + "\\" + label.toString().toLowerCase() + i + ".txt").getAbsoluteFile();
                    Scanner Reader = new Scanner(file);
                    StringBuilder data = new StringBuilder();
                    while (Reader.hasNextLine()) {
                        data.append(Reader.nextLine()).append(" ");
                    }
                    ArrayList<WordFrequency> array = new ArrayList<>(intoArrayList(stemText(clean(data.toString()))));
                    writer.write(array + "\n");
                    Reader.close();
                } catch (IOException e) {
                    System.out.println("File not found");
                    e.printStackTrace();
                }
            }
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        createArrays();
    }

}
