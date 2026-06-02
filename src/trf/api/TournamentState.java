/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package trf.api;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import trf.api.RankedPlayer.TiebreakEntry;
import trf.tiebreak.TiebreakStrategy;

/**
 *
 * @author DP
 */
public class TournamentState {

    private int currentRound;

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }
    private int nextRound;
    private final TournamentInfo tournamentInfo;
    
    private List<RankedPlayer> lastRankedResults;
    
    private RankingUpdateListener rankingListener;

          
    

    // ... Constructor ...
    public TournamentState(TournamentInfo info) {
        this.tournamentInfo = info;
        
    }

    /**
     * Führt die Berechnung basierend auf einer beliebigen Strategie aus und
     * speichert sie intern ab.
     */
    // Vorbereitung für später (vielleicht eine DB-Anbindung oder Datei-Export)
    public void archiveHistory(List<RankedPlayer> snapshot) {
        // TODO: Später Implementierung für SQLite-History oder Datei-Logging
        // Aktuell: Nur ein Platzhalter, damit die Logik-Kette steht.
        System.out.println("DEBUG: Snapshot für Runde " + currentRound + " archiviert.");
    }

    public void updateRanking() {

// 1. Vorbereiten (Previous Rank setzen)
        if (nextRound != 0) {
            currentRound = nextRound;
            nextRound = currentRound + 1;
        }
        nextRound = currentRound + 1;

        for (TournamentPlayer p : this.getTournamentPlayers().values()) {
            p.setPreviousRank(p.getActualRank());
            p.calculatePoints();
            //p.setCurrentRound(currentRound);

        }

        // 2. Neue Liste berechnen (wie besprochen)
        List<RankedPlayer> newRanking = calculateNewRanking();

        // 3. Archivieren-Hook aufrufen
        archiveHistory(newRanking);

        // 4. Den "Live"-Stand aktualisieren
        this.lastRankedResults = newRanking;
        
        // Jetzt der "Rückruf" an die UI:
        if (rankingListener != null) {
            rankingListener.onRankingUpdated(this.getLastRankedResults());
        }
        
    }

    private List<RankedPlayer> calculateNewRanking() {
        List<RankedPlayer> rankPlayers = new ArrayList<>();

        for (TournamentPlayer p : this.getTournamentPlayers().values()) {

            // 1. Sammle erst alle Tiebreaks in einer temporären Liste
            List<TiebreakEntry> tiebreakEntries = new ArrayList<>();

            for (TiebreakStrategy strategy : this.tournamentInfo.listTiebreaksStrategies()) {
                double value = strategy.calculate(p, this.getTournamentPlayers(), tournamentInfo.getTotalRounds(), currentRound);
                tiebreakEntries.add(new TiebreakEntry(strategy.getLabel(), value));
            }
            RankedPlayer r = new RankedPlayer(currentRound, tournamentInfo, p, p.getPoints(), tiebreakEntries);

            rankPlayers.add(r);

        }
        Collections.sort(rankPlayers);
        for (int i = 0; i < rankPlayers.size(); i++) {
            rankPlayers.get(i).getTournamentPlayer().setActualRank(i + 1);
            
                
        }
        
        return rankPlayers;
    }

    
    public TournamentInfo getInfo() {
        return tournamentInfo;
    }

    public Map<Integer, TournamentPlayer> getTournamentPlayers() {
        return tournamentInfo.getTournamentPlayers();
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void incrementRound() {
        //this.currentRound++;
    }

    public TournamentInfo getTournamentInfo() {
        return tournamentInfo;
    }

    public List<RankedPlayer> getLastRankedResults() {
        return lastRankedResults;
    }
    
    
    public void setRankingListener(RankingUpdateListener rankingListener) {
        this.rankingListener = rankingListener;
    }

}
