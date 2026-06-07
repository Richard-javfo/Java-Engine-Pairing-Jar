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
public class SonnebornBerger extends FideTieBreakService {

    
    private final int discardLowest;
    private final int discardHighest;

    public SonnebornBerger(int discardLowest, int discardHighest) {
        this.discardLowest = discardLowest;
        this.discardHighest = discardHighest;
    }

    public SonnebornBerger() {
        this.discardLowest = 0;
        this.discardHighest = 0;
    }
    
    
    @Override
    public double calculate(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers, int totalRounds,int actualRounds) {
        
        
        List<OpponentData> opData = getSortedOpponentData(p, allPlayers, totalRounds);

        // Die Anzahl der verbleibenden Elemente nach dem "Abschneiden" unten
        int remaining = opData.size() - discardLowest - discardHighest;
    // Wir holen uns die Liste (Sortierung ist hier egal, aber schadet nicht)
        return opData.stream()
                .skip(discardLowest) // Schneidet die X kleinsten ab
                .limit(Math.max(0, remaining)) // Behält nur die mittleren N Elemente
                .mapToDouble(data -> {
                    char res = data.round().result();
                    double oppPts = data.opponentPoints();

                    // FIDE Regel: Sieg = volle Gegnerpunkte, Remis = halbe, Niederlage = 0
                    return oppPts * data.round().resultToPoints(res);

                })
                .sum();
        
        
                
        
        
    }

    public double getByeValue() {
        return 0.5;
    }

    /**
     * Erstellt eine Liste aller Gegnerdaten (echt oder virtuell) für die
     * Buchholz-Berechnung. Nutzt die effiziente "Ergebnis-Zuerst"-Prüfung.
     *
     * @param p Der Spieler, für den wir die Daten sammeln
     * @param allPlayers Die Map mit allen Teilnehmern (Lookup für echte Gegner)
     * @param totalRounds Gesamtrundenanzahl des Turniers (Wichtig für
     * FIDE-Formel)
     * @return Eine nach Gegnerpunkten sortierte Liste für Streichwertungen
     */
    public List<OpponentData> getSortedOpponentData(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers, int totalRounds) {
        List<OpponentData> opponentList = new ArrayList<>();
        double runningScore = 0.0; // Punkte des Spielers VOR der jeweiligen Runde

        for (int i = 0; i < p.getRounds().size(); i++) {
            Round r = p.getRounds().get(i);
            char res = r.result();
            int roundNum = i + 1;
            double effectiveOpponentPoints;

            // DEINE LOGIK: Erst das Ergebnis prüfen (Schneller als Map-Lookup)
            if (res == '1' || res == '0' || res == '=' || res == '½') {
                // Reguläre Partie -> Ab in die Map
                TournamentPlayer opponent = allPlayers.get(r.opponentStartRank());

                // Falls der Gegner (warum auch immer) nicht in der Map ist -> 0.0
                effectiveOpponentPoints = (opponent != null) ? opponent.getPoints() : 0.0;
            } else {
                // IRREGULÄR (Bye, Kampflos +, -, H, Z, U etc.)
                // Hier greift die FIDE-Formel für den virtuellen Gegner
                double pointsThisRound = getPointsForResult(res);
                int remainingRounds = totalRounds - roundNum;

                effectiveOpponentPoints = runningScore + pointsThisRound + (remainingRounds * 0.5);
            }

            // In die Liste packen
            opponentList.add(new OpponentData(r, effectiveOpponentPoints));

            // Wichtig: Den runningScore für die NÄCHSTE Runde aktualisieren
            runningScore += getPointsForResult(res);
        }

        // Am Ende sortieren (Standard für Buchholz-1: Schwächster Gegner zuerst)
        opponentList.sort(Comparator.comparingDouble(OpponentData::opponentPoints));

        return opponentList;
    }

    @Override
    public String getLabel() {
        return ("SB" + discardLowest + "" + discardHighest);
    }
}
