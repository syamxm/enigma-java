package com.syamxm.enigma;

public class Main {
    public static void main(String[] args) {

        // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
        // A B C D E F G H I J K L M N O P Q R S T U V W X Y Z

        // Just run in terminal using this command : javac Main.java && java Main

        Enigma enigmaMachine = new Enigma(
                new String[] { "I", "II", "III" },
                "B",
                "AAA",
                "AAA",
                "");
        CustomEnigma customEnigmaMachine = new CustomEnigma(
                new String[] { "I", "II", "III" },
                "X",
                "KET",
                "AAA",
                "");

        char[] plaintext = "SYAMIM".toCharArray();
        char[] ciphertext1 = enigmaMachine.encrypt(plaintext);
        char[] ciphertext2 = customEnigmaMachine.encrypt(plaintext);

        System.out.println(new String(ciphertext1));
        System.out.println(new String(ciphertext2));
    }
}
