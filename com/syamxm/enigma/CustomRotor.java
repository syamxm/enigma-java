package com.syamxm.enigma;

public class CustomRotor {
    protected String name;
    protected int[] wires;
    protected int[] inverseWires;
    protected CustomRotor nextRotor;
    protected int turnoverCountdown;
    protected int innerRingPosition;

    public CustomRotor(String name, String encoding) {
        this.name = name;
        validateEncoding(encoding);
        this.wires = decodeWiring(encoding);
        this.inverseWires = inverseWiring(this.wires);
        this.nextRotor = null;
        this.turnoverCountdown = 26;
        this.innerRingPosition = 0;
    }

    public static CustomRotor Create(String name, int rotorPosition, int ringSetting) {
        CustomRotor rotor;

        switch (name) {
            // Custom rotors
            case "I":
                rotor = new CustomRotor("I", "QMJZTGFKPWLSBOXNCRYEVHIADU");
                rotor.setTurnoverLetter('M');
                break;
            case "II":
                rotor = new CustomRotor("II", "CVMHTPQXZJLWORBDUGEYKNFAIS");
                rotor.setTurnoverLetter('I');
                break;
            case "III":
                rotor = new CustomRotor("III", "AMPKIXVFQEWODNZYHBCGSLTUJR");
                rotor.setTurnoverLetter('A');
                break;
            case "IV":
                rotor = new CustomRotor("IV", "NMPJZSBAQDILKEGOYHRFXTCUVW");
                rotor.setTurnoverLetter('C');
                break;
            case "V":
                rotor = new CustomRotor("V", "JHANFBRXVQYTLDCIEOMPUZKWSG");
                rotor.setTurnoverLetter('U');
                break;
            default:
                rotor = new CustomRotor("Identity", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                rotor.setTurnoverLetter('A');
                break;
        }

        rotor.setInitialPosition(rotorPosition);
        rotor.setInnerPosition(ringSetting);
        return rotor;
    }

    public String getName() {
        return name;
    }

    public int getTurnoverCountdown() {
        return turnoverCountdown;
    }

    public void setNextRotor(CustomRotor rotor) {
        this.nextRotor = rotor;
    }

    public void setTurnoverLetter(char letter) {
        char upper = Character.toUpperCase(letter);

        if (upper < 'A' || upper > 'Z') {
            throw new IllegalArgumentException("Turnover letter must be between A and Z");
        }

        this.turnoverCountdown = upper - 'A';
        if (this.turnoverCountdown == 0) {
            this.turnoverCountdown = 26;
        }
    }

    public void setInitialPosition(int initialPosition) {
        CustomRotor savedNext = this.nextRotor;
        this.nextRotor = null;

        for (int i = 0; i < initialPosition; i++) {
            this.step();
        }

        this.nextRotor = savedNext;
    }

    public void setInnerPosition(int ringSetting) {
        int numberOfSteps = ((ringSetting % 26) + 26) % 26;

        for (int i = 0; i < 26 - numberOfSteps; i++) {
            this.stepWires();
            this.updateInverseWires();
            this.innerRingPosition = (this.innerRingPosition + 1) % 26;
        }
    }

    protected static void validateEncoding(String encoding) {
        if (encoding == null) {
            throw new IllegalArgumentException("Encoding cannot be null");
        }

        if (encoding.length() != 26) {
            throw new IllegalArgumentException("Encoding must contain exactly 26 letters");
        }

        boolean[] seen = new boolean[26];

        for (int i = 0; i < encoding.length(); i++) {
            char ch = Character.toUpperCase(encoding.charAt(i));

            if (ch < 'A' || ch > 'Z') {
                throw new IllegalArgumentException("Encoding must contain only letters A-Z");
            }

            int index = ch - 'A';
            if (seen[index]) {
                throw new IllegalArgumentException("Encoding cannot contain duplicate letters: " + ch);
            }

            seen[index] = true;
        }
    }

    protected static int[] decodeWiring(String encoding) {
        int[] wiring = new int[encoding.length()];
        for (int i = 0; i < encoding.length(); i++) {
            wiring[i] = Character.toUpperCase(encoding.charAt(i)) - 'A';
        }
        return wiring;
    }

    protected static int[] inverseWiring(int[] wiring) {
        int[] inverse = new int[wiring.length];
        for (int i = 0; i < wiring.length; i++) {
            inverse[wiring[i]] = i;
        }
        return inverse;
    }

    public int forward(int c) {
        int output = this.wires[c];
        int offset = (output - this.innerRingPosition) % 26;
        if (offset < 0) {
            offset += 26;
        }
        return offset;
    }

    public int backward(int c) {
        int offset = (c + this.innerRingPosition) % 26;
        if (offset < 0) {
            offset += 26;
        }
        return this.inverseWires[offset];
    }

    public void step() {
        this.stepWires();
        this.updateInverseWires();
        this.turnover();
        this.innerRingPosition = (this.innerRingPosition + 1) % 26;
    }

    protected void stepWires() {
        int[] newWires = new int[26];
        for (int i = 0; i < 26; i++) {
            newWires[i] = this.wires[(i + 1) % 26];
        }
        this.wires = newWires;
    }

    protected void updateInverseWires() {
        this.inverseWires = new int[26];
        for (int i = 0; i < 26; i++) {
            this.inverseWires[this.wires[i]] = i;
        }
    }

    protected void turnover() {
        this.turnoverCountdown -= 1;

        if (this.turnoverCountdown == 0) {
            if (this.nextRotor != null) {
                this.nextRotor.step();
            }
            this.turnoverCountdown = 26;
        }
    }
}