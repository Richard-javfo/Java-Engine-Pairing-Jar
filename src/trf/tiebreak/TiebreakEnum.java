/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package trf.tiebreak;

/**
 *
 * @author DP
 */
public enum TiebreakEnum {

    BUCHHOLZ_00(0, 0, "Volle Buchholz"),
    BUCHHOLZ_10(1, 0, "Buchholz (1 Streichresultat)"),
    BUCHHOLZ_11(1, 1, "Median-Buchholz"),
    SONNEBORN_BERGER(0, 0, "Sonneborn-Berger");

    private final int discardLowest;
    private final int discardHighest;
    private final String displayName;

    TiebreakEnum(int discardLowest, int discardHighest, String displayName) {
        this.discardLowest = discardLowest;
        this.discardHighest = discardHighest;
        this.displayName = displayName;
    }

    // Getter für die Kotlin-Welt
    public int getDiscardLowest() {
        return discardLowest;
    }

    public int getDiscardHighest() {
        return discardHighest;
    }

    public String getDisplayName() {
        return displayName;
    }
}
