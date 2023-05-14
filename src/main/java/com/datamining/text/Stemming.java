package com.datamining.text;

/**
 * Stemming algorithm to reduce words to their stems
 **/

public class Stemming {
    private static int wordLength;
    /**
     * The representation of the word as consonant C or vowel V
     */
    private static final StringBuilder wordM = new StringBuilder();

    public static final char[] VOWELS = {'a', 'e', 'i', 'o', 'u', 'y'};

    /**
     * Applies the stemming rules to a word
     * @param word Word that is going to be stemmed
     * @return Stem of the provided word
     */
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

    /**
     * Checks if the word ends in s, sses or ies
     * @param word The word to evaluate by its ending
     * @return A substring of the word with its ending removed
     */
    private static String step1a(String word) {
        if (word.endsWith("s")) {
            if (word.endsWith("sses")) wordLength -= 2;
            if (word.endsWith("ies")) {
                if (wordLength == 4) wordLength -= 1;
                else wordLength -= 2;
            } else if (wordLength > 3 && word.charAt(wordLength - 2) != 's' && word.charAt(wordLength - 2) != 'u' && word.charAt(wordLength - 2) != 'x'
                    && (word.charAt(wordLength-2) != 'i')) {
                wordLength --;
            }
        }
        return word.substring(0, wordLength);
    }

    /**
     * Checks if the word edns with  eed, ed and ing and removes the ending if conditions are met
     * @param word The word to evaluate by its ending
     * @return A substring of the word with its ending removed
     * */
    private static String step1b(String word) {
        if (word.endsWith("eed") && calculateM(word.substring(0, wordLength - 3)) > 0) {wordLength -= 1; word = word.substring(0, wordLength);}
        if (word.endsWith("ed") && containsVowel(word.substring(0, wordLength - 2))) {wordLength -= 2;  word = word.substring(0, wordLength); word = step1bExtension(word);}
        if (word.endsWith("ing") && containsVowel(word.substring(0, wordLength - 3))) {wordLength -= 3;  word = word.substring(0, wordLength); word = step1bExtension(word);}
        return word;
    }

    /**
     * Submethod used in the evaluation for {@link #step1b(String)} two and third conditions
     * @param word The word to evaluate by its ending
     * @return A substring of the words with its ending removed
    * */
    private static String step1bExtension(String word) {
        if (word.endsWith("at") || word.endsWith("bl") || word.endsWith("iz")) {word = word.concat("e"); wordLength++;}
        else if (endsInDoubleConsonant(word)) {
            if (!word.endsWith("l") && !word.endsWith("s") && !word.endsWith("z")) {wordLength--; word = word.substring(0, wordLength);}
        }
        else if (calculateM(word) == 1 && isO(word)) {word = word.concat("e"); wordLength++;}
        return word;
    }

    /**
     * Check if the word end in "y" and that its previous letter is a vowel
     * @param word The word to evaluate for its ending
     * @return a substring of the word with its ending removed
     */
    private static String step1c(String word) {
        if (word.endsWith("y") && containsVowel(word.substring(0, wordLength - 1))) word = word.substring(0, wordLength - 1) + 'i';
        return word;
    }

    /**
     * Check if the word has a specific ending and that its M measure is greater than 0
     * @param word The word to evaluate for its ending
     * @return a substring of the word with its ending removed
     */
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
        else if (word.endsWith("ation")) {
            if (calculateM(word.substring(0, wordLength - 6)) > 0){
                word = word.substring(0, wordLength - 3) + 'e';
            }
        }
        else if(word.endsWith("ator")) {
            if (calculateM(word.substring(0, wordLength - 4)) > 0) {
                word = word.substring(0,wordLength - 2) + 'e';
            }
        }
        else if (word.endsWith("alism")) {
            if (calculateM(word.substring(0, wordLength - 5)) > 0) {
                word=word.substring(0, wordLength - 3);
            }
        }
        else if (word.endsWith("iveness")) {
            if (calculateM(word.substring(0, wordLength - 7)) > 0) {
                word = word.substring(0, wordLength - 4);
            }
        }
        else if (word.endsWith("fulness")) {
            if (calculateM(word.substring(0,wordLength - 7)) > 0) {
                word = word.substring(0, wordLength - 4);
            }
        }
        else if (word.endsWith("ousness")) {
            if (calculateM(word.substring(0, wordLength - 7)) > 0) {
                word = word.substring(0, wordLength - 4);
            }
        }
        else if (word.endsWith("aliti")) {
            if (calculateM(word.substring(0, wordLength - 6)) > 0) {
                word = word.substring(0, wordLength - 3);
            }
        }
        else if (word.endsWith("iviti")) {
            if (calculateM(word.substring(0, wordLength - 5)) > 0) {
                word = word.substring(0, wordLength - 3) +'e';
            }
        }
        else if (word.endsWith("biliti")) {
            if (calculateM(word.substring(0, wordLength - 6)) > 0) {
                word = word.substring(0, wordLength - 5) + "le";
            }
        }
        wordLength = word.length();
        return word;
    }

    /**
     * Check if the word has a specific ending and that its M measure is greater than 0
     * @param word The word to evaluate for its ending
     * @return a substring of the word with its ending removed
     */
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

    /**
     * Check if the word has a specific ending and that its M measure is greater than 0\1
     * @param word The word to evaluate for its ending
     * @return a substring of the word with its ending removed
     */
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
                && calculateM(word.substring(0, wordLength - 3)) > 1)
            word = word.substring(0, wordLength - 3);
        else if (word.endsWith("ou") && calculateM(word.substring(0, wordLength - 2)) > 1)
            word = word.substring(0, wordLength - 2);
        if ((word.endsWith("ism") ||
                word.endsWith("ate") ||
                word.endsWith("iti") ||
                word.endsWith("ous") ||
                word.endsWith("ive") ||
                word.endsWith("ize")) && calculateM(word.substring(0, wordLength - 3)) > 1)
            word = word.substring(0, wordLength - 3);
        wordLength = word.length();
        return word;
    }


    /**
     * Check if the word ends with "e" and that other conditions are met
     * @param word The word to evaluate for its ending
     * @return a substring of the word with its ending removed
     */
    private static String step5a(String word) {
        if (word.endsWith("e")) {
            if (calculateM(word.substring(0, wordLength - 1)) > 1 || (calculateM(word.substring(0, wordLength - 1)) == 1 && !isO(word.substring(0, wordLength - 1)))) {
                word = word.substring(0, wordLength - 1);
                wordLength = word.length();
            }
        }
        return word;
    }


    /**
     * Check if the word ends with "l" and that other conditions are met
     * @param word The word to evaluate for its ending
     * @return a substring of the word with its ending removed
     */
    private static String step5b(String word) {
        if (!word.endsWith("l") && endsInDoubleConsonant(word) && calculateM(word) > 1) {
            word = word.substring(0, wordLength - 1);
            wordLength = word.length();
        }
        return word;
    }

    /**
     * Check if a letter is a vowel
     * @param letter A single letter
     * @return true if the letter is a vowel, false if not
     */
    private static boolean isVowel(char letter) {
        for (char vowel:VOWELS) {
            if(letter == vowel) return true;
        }
        return false;
    }

    /**
     * Check if a word contains a vowel
     * @param word The word to evaluate
     * @return true if there is at least a vowel the word
     */
    private static boolean containsVowel(String word) {
        for(int i = 0; i < word.length(); i++){
            if(isVowel(word.charAt(i))) return true;
        }
        return false;
    }

    /**
     * Checks if the word ends in Consonant-Vowel-Consonant
     * @param word Word that is going to be checked
     * @return true if ends in CVC and the last letter is neither w, x nor y
     */
    private static boolean isO(String word) {
        int length = word.length();
        if (length < 3) return false;
        return wordM.charAt(length - 1) == 'C' && wordM.charAt(length - 2) == 'V' && wordM.charAt(length - 3) == 'C'
                && word.charAt(length-1) != 'w' &&  word.charAt(length - 1) != 'x' &&  word.charAt(length - 1) != 'y';
    }

    /**
     * Calculate the m value of a word and update the value of the wordM variable
     * @param word The word to calculate its m value
     * @return m value of the word
     */
    private static int calculateM(String word) {
        int m = 0;
        wordM.delete(0, wordLength);
        wordM.setLength(0);

        for (int i = 0; i < word.length(); i++) {
            if (isVowel(word.charAt(i))) wordM.append('V');
            else wordM.append('C');
        }

        for (int i = 0; i < word.length() - 1; i++) {
            if(wordM.charAt(i) == 'V' && wordM.charAt(i + 1) == 'C') m++;
        }
        return m;
    }

    /**
     * Calculate if the words end in a double consonant
     * @param word The word to evaluate
     * @return true if the word ends in double consonant
     */
    private static boolean endsInDoubleConsonant(String word) {
        if (wordLength > 2)
            return !isVowel(word.charAt(word.length() - 1)) && word.charAt(word.length() - 1) == word.charAt(word.length() - 2);
        return false;
    }

    /**
     * Calculates, if an "y"  in a word should be considered as a consonant or a vowel
     * @param word The word to evaluate
     * @return a substring of the word with the "y" altered to "Y" in case it should be considered as a consonant
     */
    private static String consonantY(String word) {
        if (word.charAt(0) == 'y') word = 'Y' + word.substring(1);
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) == 'y' && isVowel(word.charAt(i - 1))) word = word.substring(0, i) + 'Y' + word.substring(i + 1);
        }
        return word;
    }
}
