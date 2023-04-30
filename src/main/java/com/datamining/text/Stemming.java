package com.datamining.text;

public class Stemming {

    public String word;

    public static final  char [] VOWELS={'a','e', 'i', 'o', 'u', 'y'};
    public static final  String [] DOBLE={"bb", "dd", "ff" , "gg",   "mm" ,"nn","pp","rr","tt"};
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

    public void step1a(){

    }



    public static void main(String[] args) {
        Stemming a=new Stemming();
        a.word="yes";
        a.Y();
        System.out.println(a.word);

    }









}
