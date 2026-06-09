/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.tiebreak;

/**
 *
 * @author DP
 */
import java.util.List;
import java.util.Map;
import trf.api.TournamentPlayer;

import trf.impl.JavaTournamentPlayer;

public class Buchholz extends FideTieBreakService {

    private final int discardLowest;
    private final int discardHighest;

    public Buchholz(int discardLowest, int discardHighest) {
        this.discardLowest = discardLowest;
        this.discardHighest = discardHighest;
    }

    public Buchholz() {
        this.discardLowest = 0;
        this.discardHighest = 0;
    }

    @Override
    public double calculate(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers, int totalRounds) {
        List<OpponentData> opData = getSortedOpponentData(p, allPlayers, totalRounds);

        // Die Anzahl der verbleibenden Elemente nach dem "Abschneiden" unten
        int remaining = opData.size() - discardLowest - discardHighest;

        return opData.stream()
                .skip(discardLowest) // Schneidet die X kleinsten ab
                .limit(Math.max(0, remaining)) // Behält nur die mittleren N Elemente
                .mapToDouble(OpponentData::opponentPoints) // DIREKTER Zugriff auf den Record-Wert
                .sum();
    }

    @Override
    public String getLabel() {
        return ("Buchholz" + discardLowest + "" + discardHighest);
    }

    
}
