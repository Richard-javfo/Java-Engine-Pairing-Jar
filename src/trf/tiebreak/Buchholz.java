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

    public Buchholz() {
        super();
    }

    public Buchholz(int l, int h) {
        super(l,h);
    }

    @Override
    public double calculate(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers, int totalRounds) {
        List<OpponentData> opData = getSortedOpponentData(p, allPlayers, totalRounds);

        // Die Anzahl der verbleibenden Elemente nach dem "Abschneiden" unten
        int remaining = opData.size() - this.getDiscardLowest() - this.getDiscardHighest();

        return opData.stream()
                .skip(getDiscardLowest()) // Schneidet die X kleinsten ab
                .limit(Math.max(0, remaining)) // Behält nur die mittleren N Elemente
                .mapToDouble(OpponentData::opponentPoints) // DIREKTER Zugriff auf den Record-Wert
                .sum();
    }

    @Override
    public String getLabel() {
        return ("Buchholz" + getDiscardLowest() + "" + getDiscardHighest());
    }

    
}
