package com.syamxm.enigma;

public class CustomEnigma {

    public CustomRotor leftRotor;
    public CustomRotor middleRotor;
    public CustomRotor rightRotor;

    public CustomReflector reflector;
    public Plugboard plugboard;

    public CustomEnigma(String[] rotors, String reflector, int[] rotorPositions, int[] ringSettings,
            String plugboardConnections) {
        this.leftRotor = CustomRotor.Create(rotors[0], rotorPositions[0], ringSettings[0]);
        this.middleRotor = CustomRotor.Create(rotors[1], rotorPositions[1], ringSettings[1]);
        this.rightRotor = CustomRotor.Create(rotors[2], rotorPositions[2], ringSettings[2]);

        this.rightRotor.setNextRotor(this.middleRotor);
        this.middleRotor.setNextRotor(this.leftRotor);

        this.reflector = CustomReflector.Create(reflector);
        this.plugboard = new Plugboard(plugboardConnections);
    }

    public CustomEnigma(String[] rotors, String reflector, String rotorPositions, String ringSettings,
            String plugboardConnections) {
        this(
                rotors,
                reflector,
                lettersToIndexes(rotorPositions, "rotorPositions"),
                lettersToIndexes(ringSettings, "ringSettings"),
                plugboardConnections);
    }

    private static int[] lettersToIndexes(String text, String fieldName) {
        if (text == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }

        text = text.trim().toUpperCase();

        if (text.length() != 3) {
            throw new IllegalArgumentException(fieldName + " must contain exactly 3 letters");
        }

        int[] result = new int[3];

        for (int i = 0; i < 3; i++) {
            char ch = text.charAt(i);

            if (ch < 'A' || ch > 'Z') {
                throw new IllegalArgumentException(fieldName + " must only contain letters A-Z");
            }

            result[i] = ch - 'A';
        }

        return result;
    }

    public void rotate() {
        if (this.middleRotor.getTurnoverCountdown() == 1 &&
                this.leftRotor.getTurnoverCountdown() == 1) {
            this.middleRotor.step();
        }

        this.rightRotor.step();
    }

    public int encrypt(int c) {
        rotate();

        c = this.plugboard.forward(c);

        int c1 = rightRotor.forward(c);
        int c2 = middleRotor.forward(c1);
        int c3 = leftRotor.forward(c2);

        int c4 = reflector.forward(c3);

        int c5 = leftRotor.backward(c4);
        int c6 = middleRotor.backward(c5);
        int c7 = rightRotor.backward(c6);

        c7 = plugboard.forward(c7);

        return c7;
    }

    public char encrypt(char c) {
        return (char) (this.encrypt(c - 'A') + 'A');
    }

    public char[] encrypt(char[] input) {
        char[] output = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = this.encrypt(input[i]);
        }
        return output;
    }

}
