package com.syamxm.enigma;

public class CustomReflector {
    protected int[] forwardWiring;

    public CustomReflector(String encoding) {
        encoding = normaliseEncoding(encoding);
        validateEncoding(encoding);
        this.forwardWiring = decodeWiring(encoding);
    }

    public static CustomReflector Create(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Reflector name cannot be null");
        }

        switch (name.toUpperCase()) {
            // Custom reflectors
            case "X":
                return new CustomReflector("MTLQZRWXPVNCAKUIDFYBOJGHSE");
            case "Y":
                return new CustomReflector("HLMPQRSATUVBCWXDEFGIJKNOZY");

            default:
                throw new IllegalArgumentException("Unknown reflector: " + name);
        }
    }

    public static CustomReflector fromPairs(String pairs) {
        return new CustomReflector(buildEncodingFromPairs(pairs));
    }

    protected static String normaliseEncoding(String encoding) {
        if (encoding == null) {
            throw new IllegalArgumentException("Encoding cannot be null");
        }
        return encoding.trim().toUpperCase();
    }

    protected static void validateEncoding(String encoding) {
        if (encoding.length() != 26) {
            throw new IllegalArgumentException("Reflector encoding must contain exactly 26 letters");
        }

        boolean[] seen = new boolean[26];

        for (int i = 0; i < 26; i++) {
            char ch = encoding.charAt(i);

            if (ch < 'A' || ch > 'Z') {
                throw new IllegalArgumentException("Reflector encoding must only contain letters A-Z");
            }

            int index = ch - 'A';
            if (seen[index]) {
                throw new IllegalArgumentException("Reflector encoding cannot contain duplicate letters: " + ch);
            }

            seen[index] = true;
        }

        for (int i = 0; i < 26; i++) {
            int mapped = encoding.charAt(i) - 'A';

            if (mapped == i) {
                throw new IllegalArgumentException(
                        "Reflector encoding cannot map a letter to itself: " + (char) ('A' + i));
            }

            int mappedBack = encoding.charAt(mapped) - 'A';
            if (mappedBack != i) {
                throw new IllegalArgumentException(
                        "Reflector encoding must be symmetric: " +
                                (char) ('A' + i) + " -> " + (char) ('A' + mapped) +
                                " but " + (char) ('A' + mapped) + " -> " + (char) ('A' + mappedBack));
            }
        }
    }

    protected static String buildEncodingFromPairs(String pairs) {
        if (pairs == null || pairs.trim().isEmpty()) {
            throw new IllegalArgumentException("Pair string cannot be null or empty");
        }

        char[] mapping = new char[26];
        boolean[] used = new boolean[26];

        String[] pairList = pairs.toUpperCase().trim().split("\\s+");

        for (String pair : pairList) {
            if (pair.length() != 2) {
                throw new IllegalArgumentException("Each reflector pair must contain exactly 2 letters: " + pair);
            }

            char a = pair.charAt(0);
            char b = pair.charAt(1);

            if (a < 'A' || a > 'Z' || b < 'A' || b > 'Z') {
                throw new IllegalArgumentException("Pairs must only use letters A-Z");
            }

            if (a == b) {
                throw new IllegalArgumentException("Reflector pair cannot map a letter to itself: " + pair);
            }

            int ai = a - 'A';
            int bi = b - 'A';

            if (used[ai] || used[bi]) {
                throw new IllegalArgumentException("Letter used more than once in reflector pairs: " + pair);
            }

            mapping[ai] = b;
            mapping[bi] = a;
            used[ai] = true;
            used[bi] = true;
        }

        for (int i = 0; i < 26; i++) {
            if (!used[i]) {
                throw new IllegalArgumentException(
                        "Reflector pairs must cover all 26 letters exactly once. Missing: " + (char) ('A' + i));
            }
        }

        return new String(mapping);
    }

    protected static int[] decodeWiring(String encoding) {
        int[] wiring = new int[encoding.length()];
        for (int i = 0; i < encoding.length(); i++) {
            wiring[i] = encoding.charAt(i) - 'A';
        }
        return wiring;
    }

    public int forward(int c) {
        return this.forwardWiring[c];
    }
}