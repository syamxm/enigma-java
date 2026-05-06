package com.syamxm.enigma;

import java.util.HashSet;
import java.util.Set;

public class Plugboard {
    private int[] wiring;

    public Plugboard(String connections) {
        this.wiring = decodePlugboard(connections);
    }

    public int forward(int c) {
        return this.wiring[c];
    }

    private static int[] identityPlugboard() {
        int[] mapping = new int[26];
        for (int i = 0; i < 26; i++) {
            mapping[i] = i;
        }
        return mapping;
    }

    public static Set<Integer> getUnpluggedCharacters(String plugboard) {
        Set<Integer> unpluggedCharacters = new HashSet<>();
        for (int i = 0; i < 26; i++) {
            unpluggedCharacters.add(i);
        }

        if (plugboard == null || plugboard.isEmpty()) {
            return unpluggedCharacters;
        }

        plugboard = plugboard.toUpperCase();
        String[] pairings = plugboard.split("[^A-Z]");

        for (String pair : pairings) {
            if (pair.length() != 2) {
                continue;
            }

            int c1 = pair.charAt(0) - 'A';
            int c2 = pair.charAt(1) - 'A';

            unpluggedCharacters.remove(c1);
            unpluggedCharacters.remove(c2);
        }

        return unpluggedCharacters;
    }

    public static int[] decodePlugboard(String plugboard) {
        if (plugboard == null || plugboard.isEmpty()) {
            return identityPlugboard();
        }

        plugboard = plugboard.toUpperCase();
        String[] pairings = plugboard.split("[^A-Z]");
        Set<Integer> pluggedCharacters = new HashSet<>();
        int[] mapping = identityPlugboard();

        for (String pair : pairings) {
            if (pair.length() != 2) {
                return identityPlugboard();
            }

            int c1 = pair.charAt(0) - 'A';
            int c2 = pair.charAt(1) - 'A';

            if (pluggedCharacters.contains(c1) || pluggedCharacters.contains(c2)) {
                return identityPlugboard();
            }

            pluggedCharacters.add(c1);
            pluggedCharacters.add(c2);

            mapping[c1] = c2;
            mapping[c2] = c1;
        }

        return mapping;
    }
}
