/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package trf.api;

/**
 *
 * @author DP
 */
public enum TrfColor {
    WHITE('w'), 
    BLACK('b'), 
    BYE('-'), 
    UNDEFINED('.');

    private final char trfChar;

    TrfColor(char trfChar) {
        this.trfChar = trfChar;
    }

    public char getTrfChar() {
        return trfChar;
    }

    /**
     * Hilfsmethode zur Bestimmung der Farbe anhand des TRF-Strings.
     * Da sie statisch ist, kann man sie bequem aufrufen.
     */
    public static TrfColor fromString(String color) {
        if (color == null) return BYE;
        return switch (color.toUpperCase()) {
            case "w", "W" -> WHITE;
            case "b", "B" -> BLACK;
            case "-", " " -> BYE;
            default -> BYE;
        };
    }
}
