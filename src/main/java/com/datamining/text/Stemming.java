package com.datamining.text;

import com.google.common.primitives.Chars;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Stemming {

    public String word;
    private int wordLength;
    StringBuilder wordM = new StringBuilder();



    public static final char[] VOWELS= {'a', 'e', 'i', 'o', 'u', 'y'};
    public static final  String [] DOUBLE={"bb", "dd", "ff" , "gg",   "mm" ,"nn","pp","rr","tt"};
    public static final  char [] LI_ENDING={'e', 'g','d','c','h','k','n','m','r','t'};

    private String r1;
    private String r2;

    private  boolean isVowel(char letter){
        for (char vowel:VOWELS) {
            if(letter==vowel)return true;

        }
        return false;
    }

    private void Y(){
        if(word.charAt(0)=='y')word='Y'+word.substring(1);
        for (int i=1; i<word.length();i++){
            if(word.charAt(i)=='y' && isVowel(word.charAt(i-1))) word=word.substring(0,i)+'Y'+word.substring(i+1);

        }


    }

    public  void calculateR1(){
        for (int i=0; i<word.length()-1;i++){
            if(isVowel(word.charAt(i)) && !isVowel(word.charAt(i+1))){
                this.r1= word.substring(i+2);
                return;
            }
        }

    }
    public  void calculateR2(){
        for (int i=0; i<r1.length()-1;i++){
            if(isVowel(r1.charAt(i)) && !isVowel(r1.charAt(i+1))){
                this.r2= r1.substring(i+2);
                return;
            }
        }

    }

    private void step1a(){

        calculateM(word);

        if(word.endsWith("s")){
            if(word.endsWith("sses")) wordLength -= 2;
            if(word.endsWith("ies")){
                if(wordLength == 4) wordLength -= 1;
                else wordLength -= 2;
                //kiwis
            }else if (wordLength > 3 && word.charAt(wordLength-2) != 's' && word.charAt(wordLength-2) != 'u' && word.charAt(wordLength-2) != 'x'
                    && (word.charAt(wordLength-2) != 'i')) {
                wordLength --;
            }
        }
        word = word.substring(0,wordLength);
    }

    private void step1b(){

        if(word.endsWith("eed") && calculateM(word.substring(0,wordLength-3)) > 0) {wordLength -= 1; word = word.substring(0,wordLength);}
        if(word.endsWith("ed") && containsVowel(word.substring(0,wordLength-2))) {wordLength -= 2;  word = word.substring(0,wordLength);  step1bExtension();;}
        if(word.endsWith("ing") && containsVowel(word.substring(0,wordLength-3))) {wordLength -= 3;  word = word.substring(0,wordLength);  step1bExtension();}




    }

    private void step1bExtension(){
        if(word.endsWith("at") || word.endsWith("bl") || word.endsWith("iz")) {word = word.concat("e");wordLength++;}
        //Checks if they are double consonants
        if(!isVowel(word.charAt(wordLength-1)) && word.charAt(wordLength-1) == word.charAt(wordLength-2)){
            if(!word.endsWith("l") && !word.endsWith("s") &&  !word.endsWith("z")) {wordLength--; word = word.substring(0,wordLength);};
        }
        if(calculateM(word) == 1 && isO()) { word = word.concat("e"); wordLength++;};

    }

    private void step2(){

    }

    private boolean containsVowel(String word){
        for(int i = 0; i < word.length(); i++){
            if(isVowel(word.charAt(i))) return true;
        }
        return false;
    }

    private boolean isO(){
        return wordM.charAt(wordLength-1) == 'C' && wordM.charAt(wordLength - 2) == 'V' && wordM.charAt(wordLength - 3) == 'C'
                && word.charAt(wordLength-1) != 'w' &&  word.charAt(wordLength-1) != 'x' &&  word.charAt(wordLength-1) != 'y';
    }
    private int calculateM(String word){

        int m = 0;


        wordLength = word.length();

        for(int i=0; i< wordLength; i++){
            if(isVowel(word.charAt(i))) wordM.append('V');
            else wordM.append('C');
        }

        for(int i=0; i<wordLength-1; i++){
            if(wordM.charAt(i) == 'V' && wordM.charAt(i+1) == 'C') m++;
        }

        return m;

    }

    public static void main(String[] args) {
        Stemming a=new Stemming();
        a.word="hoping";
        a.step1a();
        a.step1b();
        System.out.println(a.word);
    }









}
