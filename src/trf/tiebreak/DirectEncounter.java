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
public class DirectEncounter extends DiscardTiebreak {

    @Override
    public String getLabel() {
        return "Direkter Vergleich";
    }

    @Override
    public List<OpponentData> getSortedOpponentData(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers, int totalRounds) {
        return null;
    }

    @Override
    public double calculate(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers, int totalrounds) {

        List<OpponentData> opponentList = new ArrayList<>();
        double directScore = 0.0;

        for (int i = 0; i < p.getRounds().size(); i++) {
            Round r = p.getRounds().get(i);

            // Deine schnelle "Ergebnis-Zuerst"-Logik
            TournamentPlayer opponent = allPlayers.get(r.opponentStartRank());
            if (opponent != null) {
                if (opponent.getPoints() == p.getPoints()) {
                    directScore += Round.resultToPoints(r.result());
                }

            }
        }

        return directScore;

    }
}
