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
public  abstract class FideTieBreakService implements TiebreakStrategy {

    public List<OpponentData> getSortedOpponentData(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers, int totalRounds,int actualRounds) {
        List<OpponentData> opponentList = new ArrayList<>();
        double runningScore = 0.0;
        
        if(actualRounds > p.getRounds().size())
            
                try {
                    throw new Exception("Player has missing rounds");
        } catch (Exception ex) {
                    // Gibt den Klassennamen und die Fehlermeldung aus
    System.out.println("ERROR in TRFJavafoPairingProvider: " + ex.getMessage());
    // Druckt den kompletten Stacktrace in das Logcat/Konsole (sehr wichtig für die Fehlersuche!)
    ex.printStackTrace();
        }
         

        for (int i = 0; i < p.getRounds().size(); i++) {
            Round r = p.getRounds().get(i);
            char res = r.result;
            double effectiveOpponentPoints;

            // Deine schnelle "Ergebnis-Zuerst"-Logik
            if (res == '1' || res == '0' || res == '=' || res == '½') {
                TournamentPlayer opponent = allPlayers.get(r.opponentStartRank);
                effectiveOpponentPoints = (opponent != null) ? opponent.getPoints() : 0.0;
            } else {
                // Virtueller Gegner nach FIDE
                effectiveOpponentPoints = calculateVirtual(runningScore, res, totalRounds, i + 1);
            }

            opponentList.add(new OpponentData(r, effectiveOpponentPoints));
            runningScore += getPointsForResult(res);
        }

        // Sortierung: Schwächster Gegner zuerst
        opponentList.sort(Comparator.comparingDouble(OpponentData::opponentPoints));
        return opponentList;
    }

    // Diese Logik ist für alle FIDE-basierten Wertungen gleich
    protected double calculateVirtual(double runningScore, char result, int totalRounds, int roundNum) {
        double sR = getPointsForResult(result);
        return runningScore + sR + (totalRounds - roundNum) * 0.5;
    }

    protected double getPointsForResult(char res) {
        return switch (res) {
            case '1', '+' -> 1.0;
            case '=', '½', 'H', 'U' -> 0.5;
            default -> 0.0;
        };
    }

    

    
}
