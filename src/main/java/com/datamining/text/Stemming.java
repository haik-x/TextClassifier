package com.datamining.text;

public class Stemming {
    private static int wordLength;
    private static final StringBuilder wordM = new StringBuilder();

    public static final char[] VOWELS = {'a', 'e', 'i', 'o', 'u', 'y'};

    public static String stem(String word) {
        String stemmedWord = word;
        wordLength = word.length();
        stemmedWord = consonantY(stemmedWord);
        stemmedWord = step1a(stemmedWord);
        stemmedWord = step1b(stemmedWord);
        stemmedWord = step1c(stemmedWord);
        stemmedWord = step2(stemmedWord);
        stemmedWord = step3(stemmedWord);
        stemmedWord = step4(stemmedWord);
        stemmedWord = step5a(stemmedWord);
        stemmedWord = step5b(stemmedWord);
        return stemmedWord.toLowerCase();
    }

    private static String step1a(String word) {
        if (word.endsWith("s")) {
            if (word.endsWith("sses")) wordLength -= 2;
            if (word.endsWith("ies")) {
                if (wordLength == 4) wordLength -= 1;
                else wordLength -= 2;
            } else if (wordLength > 3 && word.charAt(wordLength-2) != 's' && word.charAt(wordLength-2) != 'u' && word.charAt(wordLength-2) != 'x'
                    && (word.charAt(wordLength-2) != 'i')) {
                wordLength --;
            }
        }
        return word.substring(0, wordLength);
    }

    private static String step1b(String word) {
        if (word.endsWith("eed") && calculateM(word.substring(0,wordLength-3)) > 0) {wordLength -= 1; word = word.substring(0,wordLength);}
        if (word.endsWith("ed") && containsVowel(word.substring(0,wordLength-2))) {wordLength -= 2;  word = word.substring(0,wordLength); word = step1bExtension(word);}
        if (word.endsWith("ing") && containsVowel(word.substring(0,wordLength-3))) {wordLength -= 3;  word = word.substring(0,wordLength); word = step1bExtension(word);}
        return word;
    }

    private static String step1bExtension(String word) {
        if (word.endsWith("at") || word.endsWith("bl") || word.endsWith("iz")) {word = word.concat("e"); wordLength++;}
        if (endsInDoubleConsonant(word)) {
            if (!word.endsWith("l") && !word.endsWith("s") && !word.endsWith("z")) {wordLength--; word = word.substring(0, wordLength);}
        }
        if (calculateM(word) == 1 && isO(word)) {word = word.concat("e"); wordLength++;}
        return word;
    }

    private static String step1c(String word) {
        if (word.endsWith("y") && containsVowel(word.substring(0, wordLength - 1))) word = word.substring(0, wordLength - 1) + 'i';
        return word;
    }

    private static String step2(String word) {
        if (word.endsWith("ational")) {
           if (calculateM(word.substring(0, wordLength - 7)) > 0) {
               word = word.substring(0, wordLength-5) + 'e';
               wordLength =- 4;
           }
        }
        else if (word.endsWith("tional")) {
            if (calculateM(word.substring(0, wordLength - 6)) > 0) {
                word = word.substring(0, wordLength - 2);
                wordLength =- 2;
            }
        }
        else if (word.endsWith("enci")) {
            if (calculateM(word.substring(0, wordLength - 4)) > 0) {
                word = word.substring(0, wordLength - 1) + 'e';
            }
        }
        else if (word.endsWith("anci")) {
            if (calculateM(word.substring(0, wordLength - 4)) > 0) {
                word = word.substring(0, wordLength - 1) + 'e';
            }
        }
        else if (word.endsWith("izer")) {
            if (calculateM(word.substring(0, wordLength - 4)) > 0) {
                word = word.substring(0, wordLength - 1);
            }
        }
        else if (word.endsWith("abli")) {
            if (calculateM(word.substring(0, wordLength - 4)) > 0) {
                word = word.substring(0, wordLength - 1) + 'e';
            }
        }
        else if (word.endsWith("alli")) {
            if (calculateM(word.substring(0, wordLength - 4)) > 0) {
                word = word.substring(0, wordLength - 2);
            }
        }
        else if (word.endsWith("entli")) {
            if (calculateM(word.substring(0, wordLength - 5)) > 0) {
                word = word.substring(0, wordLength - 2);
            }
        }
        else if (word.endsWith("eli")) {
            if (calculateM(word.substring(0, wordLength - 3)) > 0) {
                word = word.substring(0, wordLength - 2);
            }
        }
        else if (word.endsWith("ousli")) {
            if (calculateM(word.substring(0, wordLength - 5)) > 0) {
                word = word.substring(0, wordLength - 2);
            }
        }
        else if (word.endsWith("ization")) {
            if (calculateM(word.substring(0, wordLength - 7)) > 0) {
                word = word.substring(0, wordLength - 5) + 'e';
            }
        }
        else if(word.endsWith("ation")){
            if( calculateM(word.substring(0,wordLength-6))> 0){
                word=word.substring(0,wordLength-3)+'e';
            }
        }
        else if(word.endsWith("ator")){
            if( calculateM(word.substring(0,wordLength-4))> 0){
                word=word.substring(0,wordLength-2)+'e';
            }
        }
        else if(word.endsWith("alism")){
            if( calculateM(word.substring(0,wordLength-5))> 0){
                word=word.substring(0,wordLength-3);
            }
        }
        else if(word.endsWith("iveness")){
            if( calculateM(word.substring(0,wordLength-7))> 0){
                word=word.substring(0,wordLength-4);
            }
        }
        else if(word.endsWith("fulness")){
            if( calculateM(word.substring(0,wordLength-7))> 0){
                word=word.substring(0,wordLength-4);
            }
        }
        else if(word.endsWith("ousness")){
            if( calculateM(word.substring(0,wordLength-7))> 0){
                word=word.substring(0,wordLength-4);
            }
        }
        else if(word.endsWith("aliti")){
            if( calculateM(word.substring(0,wordLength-6))> 0){
                word=word.substring(0,wordLength-3);
            }
        }
        else if(word.endsWith("iviti")){
            if( calculateM(word.substring(0,wordLength-5))> 0){
                word=word.substring(0,wordLength-3)+'e';
            }
        }
        else if(word.endsWith("biliti")){
            if( calculateM(word.substring(0,wordLength-6))> 0){
                word=word.substring(0,wordLength-5)+"le";
            }
        }
        return word;
    }

    private static String step3(String word) {
        if (word.endsWith("icate") && calculateM(word.substring(0, wordLength - 5)) > 0) word = word.substring(0, wordLength - 3);
        else if (word.endsWith("ative") && calculateM(word.substring(0, wordLength - 5)) > 0) word = word.substring(0, wordLength - 5);
        else if (word.endsWith("alize") && calculateM(word.substring(0, wordLength - 5)) > 0) word = word.substring(0, wordLength - 3);
        else if (word.endsWith("iciti") && calculateM(word.substring(0, wordLength - 5)) > 0) word = word.substring(0, wordLength - 3);
        else if (word.endsWith("ical") && calculateM(word.substring(0, wordLength - 4)) > 0) word = word.substring(0, wordLength - 2);
        else if (word.endsWith("ness") && calculateM(word.substring(0, wordLength - 4)) > 0) word = word.substring(0, wordLength - 4);
        else if (word.endsWith("ful") && calculateM(word.substring(0, wordLength - 3)) > 0) word = word.substring(0, wordLength - 3);
        wordLength = word.length();
        return word;
    }

    private static String step4(String word) {
        if (word.endsWith("al") && calculateM(word.substring(0, wordLength - 2)) > 1)
            word = word.substring(0, wordLength - 2);
        else if ((word.endsWith("ance") || word.endsWith("ence")) && calculateM(word.substring(0, wordLength - 4)) > 1)
            word = word.substring(0, wordLength - 4);
        else if ((word.endsWith("er") || word.endsWith("ic")) && calculateM(word.substring(0, wordLength - 2)) > 1)
            word = word.substring(0, wordLength - 2);
        else if ((word.endsWith("able") || word.endsWith("ible")) && calculateM(word.substring(0, wordLength - 4)) > 1)
            word = word.substring(0, wordLength - 4);
        else if (word.endsWith("ant") && calculateM(word.substring(0, wordLength - 3)) > 1)
            word = word.substring(0, wordLength - 3);
        else if (word.endsWith("ement") && calculateM(word.substring(0, wordLength - 5)) > 1)
            word = word.substring(0, wordLength - 5);
        else if (word.endsWith("ment") && calculateM(word.substring(0, wordLength - 4)) > 1)
            word = word.substring(0, wordLength - 4);
        else if (word.endsWith("ent") && calculateM(word.substring(0, wordLength - 3)) > 1)
            word = word.substring(0, wordLength - 3);
        else if (word.endsWith("ion") && !word.endsWith("l") && !word.endsWith("s") && !word.endsWith("z")
                && calculateM(word.substring(0, wordLength - 3)) > 1) word = word.substring(0, wordLength - 3);
        else if (word.endsWith("ou") && calculateM(word.substring(0, wordLength - 2)) > 1)
            word = word.substring(0, wordLength - 2);
        if ((word.endsWith("ism") ||
                word.endsWith("ate") ||
                word.endsWith("iti") ||
                word.endsWith("ous") ||
                word.endsWith("ive") ||
                word.endsWith("ize")) && calculateM(word.substring(0, wordLength - 3)) > 1) word = word.substring(0, wordLength - 3);
        wordLength = word.length();
        return word;
    }

    private static String step5a(String word) {
        if (word.endsWith("E")) {
            if (calculateM(word) > 1 || (calculateM(word.substring(0, wordLength - 1)) == 1 && !isO(word))) {
                word = word.substring(0, wordLength - 1);
                wordLength = word.length();
            }
        }
        return word;
    }

    private static String step5b(String word) {
        if (!word.endsWith("l") && endsInDoubleConsonant(word) && calculateM(word) > 1) {
            word = word.substring(0, wordLength - 1);
            wordLength = word.length();
        }
        return word;
    }

    private static boolean isVowel(char letter) {
        for (char vowel:VOWELS) {
            if(letter == vowel) return true;
        }
        return false;
    }

    private static boolean containsVowel(String word){
        for(int i = 0; i < word.length(); i++){
            if(isVowel(word.charAt(i))) return true;
        }
        return false;
    }

    private static boolean isO(String word){
        return wordM.charAt(wordLength-1) == 'C' && wordM.charAt(wordLength - 2) == 'V' && wordM.charAt(wordLength - 3) == 'C'
                && word.charAt(wordLength-1) != 'w' &&  word.charAt(wordLength-1) != 'x' &&  word.charAt(wordLength-1) != 'y';
    }
    private static int calculateM(String word){
        int m = 0;
        wordM.delete(0, wordLength);
        for(int i=0; i< word.length(); i++){
            if(isVowel(word.charAt(i))) wordM.append('V');
            else wordM.append('C');
        }

        for(int i=0; i<word.length()-1; i++){
            if(wordM.charAt(i) == 'V' && wordM.charAt(i+1) == 'C') m++;
        }
        return m;
    }

    private static boolean endsInDoubleConsonant(String word) {
        return !isVowel(word.charAt(word.length() - 1)) && word.charAt(word.length() - 1) == word.charAt(word.length() - 2);
    }

    private static String consonantY(String word) {
        if(word.charAt(0)=='y')word='Y'+word.substring(1);
        for (int i=1; i<word.length();i++){
            if(word.charAt(i)=='y' && isVowel(word.charAt(i-1))) word = word.substring(0,i)+'Y'+word.substring(i+1);
        }
        return word;
    }

    public static void main(String[] args) {
        System.out.println(stem("Accident"));
    }
}
