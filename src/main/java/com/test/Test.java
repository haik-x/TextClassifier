package com.test;

import com.datamining.classifier.Classifier;
import com.datamining.classifier.Methods;
import com.datamining.classifier.TextType;

public class Test {
    public static void main(String[] args) {
        String text="C:\\Users\\samab\\Proyecto POO\\TextClassifier\\src\\main\\java\\com\\test\\test.txt";
        String science="C:\\Users\\samab\\Proyecto POO\\TextClassifier\\src\\main\\java\\com\\test\\pdf1.pdf";
        String politics="President Joe Biden signed a sweeping executive order on May 12 aimed at improving the country's cybersecurity and protecting federal government networks from cyber attacks. The order includes measures such as the establishment of cybersecurity performance goals for government contractors and the creation of a standardized playbook for responding to cyber incidents. The move comes after a series of high-profile cyber attacks on U.S. entities in recent months, including the Colonial Pipeline ransomware attack and the SolarWinds supply chain attack.";
        String science2 = "Scientists have made a breakthrough in the field of quantum computing by creating a device that can perform a specific type of calculation faster than any classical computer. The device, called a boson sampler, uses photons to perform a task that would take a classical computer years to complete.\n" +
                "\n" +
                "The boson sampler was developed by a team of researchers from the University of Science and Technology of China. The device is made up of a series of optical components that direct the paths of photons through a network of interconnected paths. The photons are then detected and the resulting pattern is used to perform a calculation.\n" +
                "\n" +
                "The task that the boson sampler was designed to perform is known as Gaussian boson sampling. This involves generating a random number, which is used to represent a complex problem. The boson sampler can then use this number to perform a calculation that would take a classical computer years to complete.\n" +
                "\n" +
                "The researchers tested the boson sampler by generating random numbers and using them to perform calculations. They found that the device was able to perform these calculations much faster than any classical computer could.\n" +
                "\n" +
                "This breakthrough is significant because it demonstrates the potential of quantum computing to solve complex problems that are beyond the capabilities of classical computers. It also highlights the importance of developing new technologies that can harness the power of quantum mechanics.\n" +
                "\n" +
                "The researchers believe that their work could lead to the development of new quantum computing technologies that can solve problems in a range of fields, from cryptography to drug design.\n" +
                "\n" +
                "However, the boson sampler is still a long way from being practical for everyday use. The device is currently too complex and too expensive to be used in a real-world setting. But the researchers are optimistic that future developments in quantum computing technology will make it possible to create devices that are more efficient and easier to use.\n" +
                "\n" +
                "In summary, the creation of the boson sampler represents a major breakthrough in the field of quantum computing, and could pave the way for the development of new technologies that can solve complex problems more efficiently than classical computers.";
        String label = Classifier.classifyText(text, Methods.COSINE, 7, TextType.TXT);
        String label2=Classifier.classifyText(science,Methods.EUCLIDEAN, 5, TextType.PDF);
        System.out.println(label);
        System.out.println(label2);
        Classifier c1=new Classifier(politics,3,TextType.STRING);
        System.out.println(c1.classifyText(Methods.MANHATTAN));
        Classifier c2 = new Classifier(science2,20,TextType.STRING);
        System.out.println(c2.classifyText(Methods.COSINE));
    }
}
