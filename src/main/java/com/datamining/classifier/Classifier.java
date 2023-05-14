package com.datamining.classifier;
import java.io.*;
import java.util.*;

import static com.datamining.text.Cleaning.clean;
import static com.datamining.text.StemText.stemText;
import static com.datamining.classifier.Labels.intoLabel;
import static com.datamining.text.HandlingPDF.extractPDFText;
import static com.datamining.text.HandlingTXT.extractTXTText;
public class Classifier {
    /**
     * The number of base texts stored per label
     */
    public final static int TEXTS_PER_LABEL = 10;
    public final static int BASE_TEXTS_TOTAL = TEXTS_PER_LABEL * Labels.values().length;
    public final static int LIMIT_OF_MOST_FREQUENT_WORDS=30;
    private String userText;
    private int k;
    private Labels label;
    private TextType textType;

    public Classifier (String userText, int k, TextType textType){
        setUserText(userText);
        setK(k);
        setTextType(textType);
    }
    
    public Classifier(String userText, TextType textType) {
        this(userText, 5, textType);
    }

    /**
     * Classifies a text into one of the available labels
     * @param userText text to classify provided by the user
     * @param method Preferred method to calculate distance
     * @param k The k-nearest base texts used to decide the classification. It is recommended to be set an odd number to avoid ties
     * @return String containing the name of the calculated label for the userText
    */


    public static String classifyText(String userText, Methods method, int k, TextType textType) throws KTooLargeException {

        if (k > BASE_TEXTS_TOTAL) throw new KTooLargeException();
        ArrayList<ArrayList<WordFrequency>> data = extractBaseData();
        ArrayList<WordFrequency> userStemmedText = intoArrayList(stemText(clean(getText(textType,userText))));
        Queue<DistanceLabel> knnPlane = new PriorityQueue<>();
        int labelIndex = 0;

        for (Labels label:Labels.values()) {
            for (int i = 0; i < TEXTS_PER_LABEL; i++) {
                double distance = getDistance(userStemmedText, data.get(i + (labelIndex* TEXTS_PER_LABEL)), method);
                knnPlane.add(new DistanceLabel(distance, label));
            }
            labelIndex++;
        }
        ArrayList<DistanceLabel> knn=new ArrayList<>(knnPlane);
        Collections.sort(knn);

        ArrayList<Integer>labelCount = new ArrayList<>();
        for (Labels l:Labels.values()) {
            labelCount.add(l.getIndex(), 0);
        }
        for (int i = 0; i < k; i++) {
            DistanceLabel dl=knn.get(i);
            Integer value = labelCount.get(dl.label.getIndex());
            value += 1;
            labelCount.set(dl.label.getIndex(), value);
        }


        return getMaxLabel(labelCount).toString();
   }

   public String classifyText(Methods methods){
      String label= classifyText(this.userText,methods, this.k, this.textType);
      this.label=intoLabel(label);
      return label;
   }

   private static String getText(TextType tT, String userText){
        return switch (tT){
            case PDF -> extractPDFText(userText);
            case TXT -> extractTXTText(userText);
            default -> userText;
        };
   }

    /**
     * Filters into the mehtod chosen by the user
     * @param userText Text provided by the user
     * @param baseText Current base text to compare to
     * @param method   Preferred method chosen by the user
     * @return Distance between userText and baseText
     */
   private static double getDistance(ArrayList<WordFrequency> userText, ArrayList<WordFrequency>baseText, Methods method) {
        double distance;
        switch (method) {
            case EUCLIDEAN -> distance = euclideanDistance(userText, baseText);
            case MANHATTAN -> distance = manhattanDistance(userText, baseText);
            default -> distance = cosineDistance(userText, baseText);
       }
       return distance;
   }

    /**
     * Calculates the frequency of each stem in the text and stores in a WordFrequency ArrayList
     * @param stemCountMap TreeMap containing each stem and its appereance count. Sorted by count
     * @return ArrayList containing up to the 20 repeated words in descending order
    */
   protected static ArrayList<WordFrequency> intoArrayList(TreeMap<String, Integer> stemCountMap) {
        ArrayList<WordFrequency> frequencyArray = new ArrayList<>();
        int totalWords = stemCountMap.size();
        String[] keys = stemCountMap.keySet().toArray(new String[0]);
        Integer[] values = stemCountMap.values().toArray(new Integer[0]);

        for (int i = 0; i < Math.min(keys.length, LIMIT_OF_MOST_FREQUENT_WORDS); i++) {
            String key = keys[i];
            int value = values[i];
            if (key != null)
                frequencyArray.add(new WordFrequency(key, value, totalWords));
        }
        return frequencyArray;
   }

   /**
    * Extracts the base data stored in each "Label"Arrays.txt and convert it back into an ArrayList
    * @return ArrayList that contains every ArrayList extracted
    */
   private static ArrayList<ArrayList<WordFrequency>> extractBaseData()  {
       ArrayList<ArrayList<WordFrequency>> data = new ArrayList<>();
       for (Labels label: Labels.values()) {

           try {
               File file = new File("dataset/Label/" + label + "/" + label.toString().toLowerCase() + "Arrays.txt").getAbsoluteFile();
               FileReader fr = new FileReader(file);
               BufferedReader br = new BufferedReader(fr);
               String line;

               while ((line = br.readLine()) != null) {
                   line = line.replaceAll("\\[", "").replaceAll("]", "");
                   String[] splited = line.split(", ");
                   ArrayList<WordFrequency> text = new ArrayList<>();

                   for (String s : splited) {
                       String[] s2 = s.split("=");
                       WordFrequency word = new WordFrequency(s2[0], Double.parseDouble(s2[1]));
                       text.add(word);
                   }
                   data.add(text);
               }
           } catch (IOException ex) {
               System.out.println(ex);
           }
       }
       return data;
   }

   /**
    * Manhattan distance: sum of the absolute diferences between the two points being compared
    * @param userText ArrayList of words and frequencies given by the user
    * @param baseText ArrayList of words and frequencies of the current baseText
    * @return distance between userText and baseText using the manhattan distance formula. Non-negative
    */
   private static double manhattanDistance(ArrayList<WordFrequency> userText, ArrayList<WordFrequency> baseText) {
       double totaldistance = 0;

       for (WordFrequency wordUser:userText) {
           double i = 0.1 * userText.indexOf(wordUser);

           if (baseText.contains(wordUser)) {
               WordFrequency wordBase = baseText.get(baseText.indexOf(wordUser));

               if(wordBase.word.equals(wordUser.word)) {
                   double j = 0.1 * userText.indexOf(wordUser);
                   totaldistance += Math.abs((wordBase.frequency - i)-(wordUser.frequency - j));
               }
           } else
               // sums wordfrecuency and
               totaldistance += wordUser.frequency+(0.01*(-i+LIMIT_OF_MOST_FREQUENT_WORDS));
       }
       return totaldistance;
   }

    /**
     * Euclidean distance: calculate the shortest path between two points using Pythagorean theorem
     * @param userText ArrayList of words and frequencies given by the user
     * @param baseText ArrayList of words and frequencies of the current baseText
     * @return distance between userText and baseText using the euclidean distance formula. Range goes from 0 to 1
     */
    private static double euclideanDistance(ArrayList<WordFrequency> userText, ArrayList<WordFrequency> baseText) {
        double distance = 0;

        for (int userTextIndex = 0; userTextIndex < userText.size(); userTextIndex++) {
            WordFrequency word = userText.get(userTextIndex);
            if (baseText.contains(word)) {
                int baseTextIndex = baseText.indexOf(word);
                distance += Math.pow((word.frequency + (userTextIndex * 0.01)) - (baseText.get(baseTextIndex).frequency+ (baseTextIndex * 0.01)), 2);

            } else {
                distance += (word.frequency)+(0.01*(-userTextIndex+(LIMIT_OF_MOST_FREQUENT_WORDS)));
            }
        }

        return Math.sqrt(distance);
    }
    /**
     * Cosine distance: Quantify the cosine of the angle between two vectors
     * @param userText ArrayList of words and frequencies given by the user
     * @param baseText ArrayList of words and frequencies of the current baseText
     * @return distance between userText and baseText using the cosine distance formula. Range goes from -1 to 0
     */
    private  static double cosineDistance(ArrayList<WordFrequency> userText, ArrayList<WordFrequency> baseText) {
        double distance = 0;

        for(int i = 0; i < baseText.size(); i++){
            WordFrequency wd = baseText.get(i);
            double userIndex = userText.indexOf(wd) + 1 * 0.01;
            if(userText.contains(wd)) {
                double userFreq = userText.get(userText.indexOf(wd)).frequency;

                distance +=
                        (((userIndex*wd.frequency) + ((i + 1) * 0.01 * userFreq))) /
                                (Math.sqrt((wd.frequency*wd.frequency) + (((i + 1) * 0.01) * ((i + 1) * 0.1))) +
                                        Math.sqrt((userFreq * userFreq) + (userIndex * userIndex)));
            }
            else{
                distance += wd.frequency+(0.01*(-userIndex+(LIMIT_OF_MOST_FREQUENT_WORDS+1) ));
            }
        }
        return distance;
    }

    /**
     * @param labelCount list with the count value for each label stored in its corresponding label index
     * @return Labels with the highest count
    */
    private static Labels getMaxLabel(ArrayList<Integer> labelCount) {
        int max = 0;
        for (Labels label: Labels.values()) {
            if (labelCount.get(label.getIndex()) > max)
                max = labelCount.get(label.getIndex());
        }
        return intoLabel(labelCount.indexOf(max));
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setLabel(Labels label) {
        this.label = label;
    }

    public void setTextType(TextType textType) {
        this.textType = textType;
    }

    public String getUserText() {
        return userText;
    }

    public int getK() {
        return k;
    }

    public Labels getLabel() {
        return label;
    }

    public TextType getTextType() {
        return textType;
    }



}
