package com.datamining.training;

import com.datamining.classifier.WordFrequency;
import static com.datamining.classifier.Classifier.intoQueue;
import static com.datamining.text.Cleaning.clean;
import static com.datamining.text.StemText.stemText;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Training {
    /*
        Por cada texto base:
            - Limpiar
            - Stem
            - StemText

            - Priority Queue a Cada elemento de mapa a WordFrequency
     */
    public static final String[] labels = {"Politics", "Sports", "Entertainment"};
    public static final int docsPerLabel = 3;
    private static void createQueues() {
        for (String label: labels) {
            File queuesFile;
            FileWriter writer;

            try {
                queuesFile = new File("dataset\\Label\\" + label + "\\" + label.toLowerCase() + "Queues.txt").getAbsoluteFile();
                writer = new FileWriter(queuesFile);
                writer.write("");
                writer = new FileWriter(queuesFile, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (int i = 1; i <= docsPerLabel; i++) {
                try {
                    File file = new File("dataset\\Label\\" + label + "\\" + label.toLowerCase() + i + ".txt").getAbsoluteFile();
                    Scanner Reader = new Scanner(file);
                    StringBuilder data = new StringBuilder();
                    while (Reader.hasNextLine()) {
                        data.append(Reader.nextLine()).append(" ");
                    }
                    PriorityQueue<WordFrequency> queue = new PriorityQueue<>(intoQueue(stemText(clean(data.toString()))));
                    writer.write(queue + "\n");
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
        createQueues();
    }

}
