package com.datamining.classifier;
import java.io.*;
import java.util.*;

import static com.datamining.text.Cleaning.clean;
import static com.datamining.text.StemText.stemText;
import static com.datamining.classifier.Labels.intoLabel;

public class Classifier {
    /**
     * The number of base texts stored per label
     */
    public final static int TEXTS_PER_LABEL = 5;
    public final static int BASE_TEXTS_TOTAL = TEXTS_PER_LABEL * Labels.values().length;

    /**
     * Classifies a text into one of the available labels
     * @param userText text to classify provided by the user
     * @param method Preferred method to calculate distance
     * @param k The k-nearest base texts used to decide the classification. It is recommended to be set an odd number to avoid ties
     * @return String containing the name of the calculated label for the userText
    */
    public static String classifyText(String userText, Methods method, int k) throws KTooLargeException {
        if (k > BASE_TEXTS_TOTAL) throw new KTooLargeException();
        ArrayList<ArrayList<WordFrequency>> data = extractBaseData();
        ArrayList<WordFrequency> userStemmedText = intoArrayList(stemText(clean(userText)));
        Queue<DistanceLabel> knnPlane = new PriorityQueue<>();
        int labelIndex = 0;

        for (Labels label:Labels.values()) {
            for (int i = 0; i < TEXTS_PER_LABEL; i++) {
                double distance = getDistance(userStemmedText, data.get(i + (labelIndex* TEXTS_PER_LABEL)), method);
                knnPlane.add(new DistanceLabel(distance, label));
            }
            labelIndex++;
        }

        ArrayList<Integer>labelCount = new ArrayList<>();
        for (Labels l:Labels.values()) {
            labelCount.add(l.getIndex(), 0);
        }

        for (int i = 0; i < k; i++) {
            DistanceLabel dl=knnPlane.poll();
            Integer value = labelCount.get(dl.label.getIndex());
            value = value == null ? 0:value + 1;
            labelCount.set(dl.label.getIndex(), value);
        }

        System.out.println(labelCount);

        return getMaxLabel(labelCount).toString();
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

        for (int i = 0; i < Math.min(keys.length, 20); i++) {
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
               totaldistance += wordUser.frequency;
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
                distance += (word.frequency);
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
            if(userText.contains(wd)) {
                double userFreq = userText.get(userText.indexOf(wd)).frequency;
                double userIndex = userText.indexOf(wd) + 1 * 0.01;
                distance +=
                        (((userIndex*wd.frequency) + ((i + 1) * 0.01 * userFreq))) /
                                (Math.sqrt((wd.frequency*wd.frequency) + (((i + 1) * 0.01) * ((i + 1) * 0.1))) +
                                        Math.sqrt((userFreq * userFreq) + (userIndex * userIndex)));
            }
            else{
                distance += wd.frequency;
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

    public static void main(String[] args) throws IOException {
//        String text1 = "The lights dimmed in the movie theater and the previews began. The audience sat in anticipation, munching on their popcorn and sipping their sodas. And then, the opening credits started rolling - the music swelled and the screen came to life. The movie was a sweeping epic, filled with action, drama, and romance. The audience was swept away by the story, cheering at the hero's triumphs and crying at his losses. By the time the credits rolled again, the audience knew they had just experienced something special - a movie that would stay with them forever.";
//        String text2 = "The film festival was in full swing, with movies from all over the world being screened in theaters around the city. Film buffs and casual moviegoers alike lined up to see the latest and greatest in cinema. There were comedies that had the audience laughing out loud, dramas that left them in tears, and documentaries that opened their eyes to new worlds. After each movie, there was a buzz in the air as people discussed what they had just seen. Some were blown away by the performances, others by the cinematography, and still others by the sheer creativity of the storytelling. For these movie lovers, the film festival was a highlight of the year - a chance to see the best of the best on the big screen.";
//
//        System.out.println(classifyText(text1, Methods.EUCLIDEAN,4));
//
//
//        String e1 = "Most moviegoers can pinpoint one summer movie – or perhaps, a summer of movies – in their formative years that really and truly cemented their love for going to the cinema, whether it be 1975’s “Jaws,” “E.T. the Extra-Terrestrial” from 1982, or “Independence Day” in 1996. Sure, a lot has happened since those golden years of the bustling multiplex – most notably the streaming revolution, not to mention a multi-year pandemic – but studio heads at last week’s CinemaCon (the annual convention where Hollywood shows theater owners what they have in store for the coming year) were quick to thump their chests and say, “Movies are back!” For proof, one need only look at this year’s billion-dollar-grossing “Super Mario Bros. Movie” (not to mention last year’s “Avatar” sequel) to see that theatrical movies, as it were, never really left. And while streaming has clearly siphoned off part of the audience, particularly for more serious films, what we think of as “summer movies” still have the potential to rake in cash just like the old days. With that said, at this juncture just ahead of the summer months, behold the list of flicks releasing “only in theaters” that are hoping to do exactly that, in order of release date:";
//        System.out.println(classifyText(e1, Methods.MANHATTAN, 3));
        /*
        String e2 = "Prepare to be transported back to the desert planet of Arrakis in the new “Dune: Part Two” trailer, where Paul Atreides (Timothée Chalamet) and Chani (Zendaya) are together at last. The first official look at the long-awaited second installment of the sweeping sci-fi film series based on Frank Herbert’s iconic 1965 novel debuted on Wednesday, quenching viewers’ thirst for new “Dune” content after the first film was released in October 2021. Director Denis Villeneuve promised the audience during the Warner Bros. Studios presentation at CinemaCon last week that “Dune: Part Two” is much more “action-packed” and an “epic war movie,” following the first film’s “contemplative” nature. By the looks of the trailer, his words ring true. (Warner Bros. Studios and CNN are all part of Warner Bros. Discovery.) Chalamet and Zendaya are seen together in the trailer picking back up where they left off in the deep desert lands of Arrakis, as they navigate their characters’ love for each other while preparing for impending wa. “We really see Paul Atreides become a leader here,” Chalamet said at CinemaCon, after explaining that the first film saw his character more through the lens of a “student.” We get a glimpse in the trailer of Atreides’ blue eyes, a transformation caused by his use of Spice, the planet’s most precious commodity that gives its user mystical powers. We’re even treated to clips of Atreides mounting a giant sandworm, and sharing a special kiss with Chani.";
        String e3 = "“Chris Evans and Ana de Armas” is about all that’s required to make the sales pitch for “Ghosted,” a spirited if familiar action-based romantic comedy, where the sparring banter generally outshines the muscular stunts. Throw in clever cameos and this Apple TV+ movie delivers on its promise of unpretentious fun. “Ghosted” offers a reminder that Evans possessed considerable appeal (including his work as a romantic interest in “The Nanny Diaries”) before he picked up Captain America’s shield, and de Armas has super-spy credentials from the James Bond movie “No Time to Die” and “The Gray Man,” one of her previous collaborations (along with the more successful “Knives Out”) with Evans. Director Dexter Fletcher (“Rocketman”) relies on the natural chemistry between the two to carry the simple premise, which begins with the unassuming Cole (Evans), who works on his family farm, meeting de Armas’ alluring Sadie, arguing with her about whether she has what it takes to raise a houseplant. From there, the pair go on one very long, very good first date. Coming off a breakup, Cole is understandably smitten, and wounded when she doesn’t immediately respond to his texts. So with encouragement from his parents (Amy Sedaris and Tate Donovan), and ignoring his sister’s warning that he “came on way too strong,” he impulsively tracks a left-behind item of his to her location in London, where the surprise turns out to be on him, as Sadie is quickly revealed to be a clandestine operative for the CIA. Moreover, shades of “North by Northwest,” the bad guys, led by Adrien Brody’s sneering villain, mistake Cole for the mysterious agent who has been mucking up their plans, dragging him into Sadie’s cloak-and-dagger quest for a dangerous bioweapon that Hitchcock used to refer to as a MacGuffin. Mostly, the spy shenanigans serve as a backdrop to the squabbling and making up that goes with Cole’s shock about Sadie’s secret life, the danger to which he has inadvertently exposed them both and the fact that everyone keeps telling the pair that they should “get a room” when they’re arguing. With a screenplay from the “Deadpool” team of Rhett Reese and Paul Wernick and “Spider-Man: No Way Home” scribes Chris McKenna and Erik Sommers, “Ghosted” exhibits a sense of playfulness throughout, with Evans appearing to relish the opportunity to portray an everyman thrust into all these heroic hijinks. (While they go to great lengths to stress that Cole wrestled in high school, in these bouts, as Sadie informs him, he can’t expect his out-for-blood opponents to tap out.) Frankly, the star power alone might have made “Ghosted” a reasonably solid box-office attraction, but with the movie heading directly to streaming – where just garnering attention is more than half the battle – it’s pretty much a slam dunk for the posters alone. Unlike the needy Cole, “Ghosted” doesn’t ask for anything more than a couple intermittently attentive hours of your time. Thanks to Evans and de Armas, it’s the sort of invitation that’s pretty hard to ignore.";
        String p1 = "Vivek Ramaswamy, a 37-year-old entrepreneur and author running for president as a Republican, has never run for elective office before, but he has clearly picked up the art of the stump speech. Here are five of his most reliable applause lines over a few days on the trail in New Hampshire. “I will be the first presidential candidate to say I will end race-based affirmative action.” It is a questionable assertion, because Ben Carson made ending affirmative action central to his 2016 campaign. But to the overwhelmingly white audiences that Mr. Ramaswamy, the son of Indian immigrants, is addressing, the promise goes over well. It fits in with his broader criticism of group identity and of the praise for diversity that is fundamental to liberal politics. But his pledge to end racial preferences by executive order could be more complicated than he makes it sound. “I will shut down the fourth branch of government, the administrative state. You cannot tame that beast. You must end it.” Mr. Ramaswamy insists that he will go much further than former President Donald J. Trump did to “drain the swamp” of the “Deep State.” And he says he will do it unilaterally, ending Civil Service protections by executive order, imposing eight-year term limits on federal positions, shuttering the Education Department and replacing the F.B.I., the I.R.S., and other agencies. The notion that “those elected to government should actually run the government” is central to his campaign, which demonizes the unelected bureaucracy that he says runs Washington. “We will use our military to annihilate the Mexican drug cartels.” While in Keene, N.H., on Wednesday, Mr. Ramaswamy mused about using a local precision-weapons plant to elaborate on his threat of military action against organized crime across the southern border in Mexico. Never mind that such a strike would be against a U.S. ally and neighbor. Mr. Trump made similar threats but never carried them out. And Mr. Ramaswamy has conceded that among some libertarian-minded voters, the promise sou ds disconcertingly bellicose. “How about a constitutional amendment to make the voting age 25, but you can still vote at 18 if you serve the country or pass the civics test my mother passed to become a citizen?” The proposal might not win the hearts of Generation Z, but it appeals to older Republican primary voters who believe the country has lost its sense of citizenship and purpose. It might also resonate with those who understand how lopsided the youth vote is in favor of Democrats. “Today we depend on our main enemy for our entire modern way of life. That is a problem. The Declaration of Independence that I will sign as your next president will be our Declaration of Independence from Communist China.” Mr. Ramaswamy says confronting China would be his top foreign policy priority, and it will entail short-term pain. He would prevent American businesses from expanding into Chinese markets unless “our demands are met” by Beijing. Those include more intellectual property protections and an end to required joint ventures with state-controlled businesses. Unwinding consumer dependence on China would be difficult and economically distressing, he concedes, but he said the endeavor would be the essence of citizen sacrifice and would forge national unity.";
        String t1 = "I love playing football with my friends";
        String t2 = "I hate waching and playing basketball";
        String t3 = "When I was a kid I was playing football with my friends every day all the evening";
        ArrayList<WordFrequency>q1=intoArrayList( stemText( clean(t1)));
        ArrayList<WordFrequency>q2=intoArrayList( stemText( clean(t2)));
        ArrayList<WordFrequency>q3=intoArrayList( stemText( clean(t3)));
        ArrayList<WordFrequency>q4=intoArrayList( stemText( clean(p1)));
        System.out.println(q1);
        System.out.println(q2);
        System.out.println(q3);
        System.out.println(q4);
        System.out.println(euclideanDistance(q1,q2));
        System.out.println(euclideanDistance(q1,q3));
        System.out.println(euclideanDistance(q1,q4));
        System.out.println();
        System.out.println(manhattanDistance(q1,q2));
        System.out.println(manhattanDistance(q1,q3));
        System.out.println(manhattanDistance(q1,q4));
        System.out.println();
        System.out.println(cosineDistance(q1,q2));
        System.out.println(cosineDistance(q1,q3));
        System.out.println(cosineDistance(q1,q4));*/


        String test = "Biologists who have been obtaining DNA sequences online from companies will soon have a more convenient option: benchtop machines that can print all the DNA they need. But this technology brings with it new risks by circumventing how synthetic biology companies now screen for would-be bioterrorists. A report released yesterday by a Washington, D.C., think tank urges companies and governments to revamp existing screening to prevent someone with malign motives from making a toxin or pathogen. The current screening system, which is voluntary, “could be upended by benchtop DNA synthesis,” says report co-author Jaime Yassif, vice president for global biological policy and programs at the Nuclear Threat Initiative. “Governments, industry, and the broader scientific community need to put stronger safeguards in place to ensure this technology is not exploited by malicious actors and that it doesn’t lead to a catastrophic accident,”she says.";
        System.out.println(classifyText(test, Methods.EUCLIDEAN, 15));
        System.out.println(BASE_TEXTS_TOTAL);
    }
}
