 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.api;

import trf.impl.JavaTournamentPlayer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DP
 */
public record RankedPlayer(
       
        //int currentRound, 
        TournamentInfo tournamentInfo,
        TournamentPlayer player ,
        double points,
        List<TiebreakEntry> tiebreaks
        ) implements Comparable<RankedPlayer> {

   


    
    // Hier schachtelst du den zweiten Record einfach hinein
    public record TiebreakEntry(String label, double value) {

    }
    // ... dein Comparator

    @Override
    public int compareTo(RankedPlayer otherPlayer) {
    // 1. Hauptpunkte (Absteigend)
    if (Double.compare(otherPlayer.points(), this.points()) != 0) {
        return Double.compare(otherPlayer.points(), this.points());
    }

    // 2. Feinwertungen (Buchholz, Sonneborn-Berger etc. in deiner tiebreaks-Liste)
    for (int i = 0; i < tiebreaks.size(); i++) {
        double v1 = this.tiebreaks().get(i).value();
        double v2 = otherPlayer.tiebreaks().get(i).value();
        if (Double.compare(v2, v1) != 0) {
            return Double.compare(v2, v1);
        }
    }

    // 3. ELO (International)
    if (Integer.compare(otherPlayer.getElo(), this.getElo()) != 0) {
        return Integer.compare(otherPlayer.getElo(), this.getElo());
    }
    
    // 4. National Elo (NWZ/DWZ)
    if (Integer.compare(otherPlayer.getNationalElo(), this.getNationalElo()) != 0) {
        return Integer.compare(otherPlayer.getNationalElo(), this.getNationalElo());
    }

    // 5. Fallback: Alphabetisch (Aufsteigend A-Z)
    String name1 = (this.getName() != null) ? this.getName() : "";
    String name2 = (otherPlayer.getName() != null) ? otherPlayer.getName() : "";

    return name1.compareToIgnoreCase(name2);
}

    // Hilfsmethode für den schnellen Zugriff auf Namen
    public String getName() {
        return player.getName();
    }

    public int getElo() {
        return player.getElo();
    }
    
    private int getNationalElo() {
        return player.getNationalElo(); 
    }
    
    public int getActualRank() {
        return player.getActualRank();
    }
    
    public TournamentPlayer getTournamentPlayer(){
        return player;
    }
}
