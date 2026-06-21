/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.tiebreak;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import trf.api.Round;
import trf.api.TournamentPlayer;

/**
 *
 * @author DP
 */
public class NationalEloAvg extends DiscardTiebreak {

    
    
    @Override
    public String getLabel() {
        return ("ELO Average" + this.getDiscardLowest() + "" + this.getDiscardHighest());}

     @Override
    public List<OpponentData> getSortedOpponentData(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers, int totalRounds) {
        List<OpponentData> opponentList = new ArrayList<>();
       
          

        for (int i = 0; i < p.getRounds().size(); i++) {
            Round r = p.getRounds().get(i);
            char res = r.result();
            double effectiveOpponentPoints;

           
                TournamentPlayer opponent = allPlayers.get(r.opponentStartRank());
                effectiveOpponentPoints = (opponent != null) ? opponent.getNationalElo() : 0.0;
          

            opponentList.add(new OpponentData(r, effectiveOpponentPoints));
            
        }

        // Sortierung: Schwächster Gegner zuerst
        opponentList.sort(Comparator.comparingDouble(OpponentData::opponentPoints));
        return opponentList;
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
    
}
