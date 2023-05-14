package com.datamining.classifier;
import java.io.*;
import java.util.*;

import static com.datamining.text.Cleaning.clean;
import static com.datamining.text.StemText.stemText;
import static com.datamining.classifier.Labels.intoLabel;
import static com.datamining.text.HandlingPDF.extractPDFText;
import static com.datamining.text.HandlingTXT.extractTXTText;

/**
 * This Class contains the method to classify a text into one of th eavailable labels
 */
public class Classifier {
    public final static int TEXTS_PER_LABEL = 10;
    public final static int BASE_TEXTS_TOTAL = TEXTS_PER_LABEL * Labels.values().length;
    public final static int LIMIT_OF_MOST_FREQUENT_WORDS = 30;

    private String userText;
    private int k;
    private Labels label;
    private TextType textType;

    /**
     * Constructs a Classifier object with the values specified by the user
     * @param userText String containing the text or the path of the file containing the text to classify
     * @param k The k-nearest base texts used to decide the classification
     * @param textType Type of text contained in userText. Either a path to a PDF or TXT or the text as a String
     */
    public Classifier (String userText, int k, TextType textType) {
        setUserText(userText);
        setK(k);
        setTextType(textType);
        setLabel(null);
    }

    /**
     * Constructs a Classifier object with the values specified by the user and a default k value of 5
     * @param userText String containing the text or the path of the file containing the text to classify
     * @param textType Type of text contained in userText. Either a path to a PDF or TXT or the text as a String
     */
    public Classifier(String userText, TextType textType) {
        this(userText, 5, textType);
    }

    /**
     * Classifies a text into one of the available labels using the specified values
     * @param userText text to classify provided by the user
     * @param method Preferred method to calculate distance
     * @param k The k-nearest base texts used to decide the classification
     * @return String containing the name of the calculated label for the userText
    */
    public static String classifyText(String userText, Methods method, int k, TextType textType) throws KTooLargeException {

        if (k > BASE_TEXTS_TOTAL) throw new KTooLargeException();

        ArrayList<ArrayList<WordFrequency>> baseData = extractBaseData();
        ArrayList<WordFrequency> userStemmedText = intoArrayList(stemText(clean(getText(textType,userText))));
        Queue<DistanceLabel> knnPlane = new PriorityQueue<>();

        // Calculate distance between userText and each baseText in baseData
        int labelIndex = 0;
        for (Labels label:Labels.values()) {
            for (int i = 0; i < TEXTS_PER_LABEL; i++) {
                double distance = getDistance(userStemmedText, baseData.get(i + (labelIndex * TEXTS_PER_LABEL)), method);
                knnPlane.add(new DistanceLabel(distance, label));
            }
            labelIndex++;
        }
        ArrayList<DistanceLabel> knn = new ArrayList<>(knnPlane);
        Collections.sort(knn);

        // Count how many base texts of each label are in within the k-nearest texts
        ArrayList<Integer> labelCount = new ArrayList<>();
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

    /**
     * Assigns a Labels to the label property and returns its String value, using the specified distance method
     * @param methods Method used to calculate distance betwee texts
     * @return The String value of the Labels assigned to the label property
     */
   public String classifyText(Methods methods){
      String label = classifyText(this.userText,methods, this.k, this.textType);
      setLabel(intoLabel(label));
      return label;
   }

    /**
     * Filters into method to extract a String from the userText based on the specified textType
     * @param textType TextType used to determine the correct method
     * @param userText String containing the file path or the text provided by the user
     * @return A String with the content of the file path or text in userText
     */
   private static String getText(TextType textType, String userText){
        return switch (textType){
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
   private static ArrayList<ArrayList<WordFrequency>> extractBaseData() {
       ArrayList<ArrayList<WordFrequency>> data = new ArrayList<>();
       for (Labels label: Labels.values()) {

           try {
               File file = new File("dataset\\Label\\" + label + "\\" + label.toString().toLowerCase() + "Arrays.txt").getAbsoluteFile();
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
               throw new RuntimeException(ex);
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
                   totaldistance += Math.abs((wordBase.frequency - i) - (wordUser.frequency - j));
               }
           } else
               // If word in the user text is not found, add its frequency plus an index based value to distance
               totaldistance += wordUser.frequency + (0.01 * (-i + LIMIT_OF_MOST_FREQUENT_WORDS));
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
                distance += Math.pow((word.frequency + (userTextIndex * 0.01)) - (baseText.get(baseTextIndex).frequency + (baseTextIndex * 0.01)), 2);

            } else {
                // If word in the user text is not found, add its frequency plus an index based value to distance
                distance += (word.frequency) + (0.01 * (-userTextIndex + LIMIT_OF_MOST_FREQUENT_WORDS));
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
                        (((userIndex * wd.frequency) + ((i + 1) * 0.01 * userFreq))) /
                                (Math.sqrt((wd.frequency * wd.frequency) + (((i + 1) * 0.01) * ((i + 1) * 0.1))) +
                                        Math.sqrt((userFreq * userFreq) + (userIndex * userIndex)));
            } else {
                // If word in the user text is not found, add its frequency plus an index based value to distance
                distance += wd.frequency + (0.01 * (-userIndex + (LIMIT_OF_MOST_FREQUENT_WORDS + 1)));
            }
        }
        return distance;
    }

    /**
     * Returns the most repeated Labels in the k-nearest documents
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

    /**
     * Replaces the text or file path with the specified String
     * @param userText New text or file path to replace the old one with
     */
    public void setUserText(String userText) {
        this.userText = userText;
    }

    /**
     * Replaces the k value with a new one
     * @param k New k value to replace the old one with
     * @throws KTooLargeException When k is larger than the number of available base texts
     */
    public void setK(int k) throws KTooLargeException {
        if (k > BASE_TEXTS_TOTAL) throw new KTooLargeException();
        this.k = k;
    }

    /**
     * Replaces the type of text the userText is considered. This will change how the userText is processed
     * @param textType TextType to replace the old one with
     */
    public void setTextType(TextType textType) {
        this.textType = textType;
    }

    /**
     * Assign a Labels to the label property
     * @param label Labels value result of applying the classifyText method
     */
    private void setLabel(Labels label) {
        this.label = label;
    }

    /**
     * Returns the current value of the userText property
     * @return The current value of the userText property
     */
    public String getUserText() {
        return userText;
    }

    /**
     * Returns the current value of the k property
     * @return The current value of the k property
     */
    public int getK() {
        return k;
    }

    /**
     * Returns the current value of the label property
     * @return The current value of the label property
     */
    public Labels getLabel() {
        return label;
    }

    /**
     * Returns the current value of the textType property
     * @return The current value of the textType property
     */
    public TextType getTextType() {
        return textType;
    }
}
