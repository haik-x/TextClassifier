package com.datamining.classifier;

import static com.datamining.classifier.Classifier.TEXTS_PER_LABEL;
import static com.datamining.classifier.Classifier.intoArrayList;
import static com.datamining.text.Cleaning.clean;
import static com.datamining.text.StemText.stemText;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;


/**
 *   Processes the dataset to train the model and be able to execute future clasiffications
 * */
public class UpdateBaseText {

    /**
     * Operates per label
     * It opens the files, applies the text processing steps and calculate frequencies of words with
     * {@link com.datamining.classifier.Classifier#intoArrayList(TreeMap)}}
     * Saves the result in a new array and adds it to the updated base text dataset
     * */
    private static void train() {
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

            for (int i = 1; i <= TEXTS_PER_LABEL; i++) {
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


    /***
     * Main method called when the training must be done
     */
    public static void main(String[] args) {
        train();
    }
}
