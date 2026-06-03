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
    // --- BUCHHOLZ VARIANTEN ---
    BUCHHOLZ_00(0, 0, "Volle Buchholz"),
    BUCHHOLZ_10(1, 0, "Buchholz (1 Streichresultat)"),
    BUCHHOLZ_11(1, 1, "Median-Buchholz"),
    BUCHHOLZ_20(2, 0, "Buchholz (2 Streichresultate)"),

    // --- SONNEBORN-BERGER VARIANTEN ---
    SONNEBORN_BERGER(0, 0, "Sonneborn-Berger (Klassisch)"),
    SONNEBORN_BERGER_10(1, 0, "Sonneborn-Berger (1 Streichresultat)"),
    SONNEBORN_BERGER_20(2, 0, "Sonneborn-Berger (2 Streichresultate)"),

    // --- WEITERE FEINWERTUNGEN ---
    DIRECT_ENCOUNTER(0, 0, "Direkter Vergleich"),
    CUMULATIVE(0, 0, "Fortschrittswertung (Summenscore)"),
    NUMBER_OF_WINS(0, 0, "Anzahl der Siege"),
    NUMBER_OF_WINS_BLACK(0, 0, "Anzahl der Siege mit Schwarz"),
    GREATER_NUMBER_OF_GAMES_BLACK(0, 0, "Höhere Anzahl an Schwarzpartien");

    private final int discardLowest;
    private final int discardHighest;
    private final String displayName;

    private TiebreakEnum(int discardLowest, int discardHighest, String displayName) {
        this.discardLowest = discardLowest;
        this.discardHighest = discardHighest;
        this.displayName = displayName;
    }

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
