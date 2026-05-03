/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.impl;

import java.util.Comparator;
import trf.api.TournamentPlayer;

/**
 *
 * @author DP
 */
public class EloComparator implements Comparator<TournamentPlayer> {

    @Override
    public int compare(TournamentPlayer p1, TournamentPlayer p2) {
        
        
        // 1. Elo-Vergleich (Absteigend: p2 gegen p1)
        if (p1.getElo() != p2.getElo()) {
            return Integer.compare(p2.getElo(), p1.getElo()); 
        }
        
        // 1. Elo-Vergleich (Absteigend: p2 gegen p1)
        if (p1.getNationalElo() != p2.getNationalElo()) {
            return Integer.compare(p2.getElo(), p1.getElo()); 
        }
        
        // 3. Fallback: Alphabetisch (Aufsteigend: p1 gegen p2)
        // null-Check zur Sicherheit, falls ein Name fehlt
        String name1 = (p1.getName() != null) ? p1.getName() : "";
        String name2 = (p2.getName() != null) ? p2.getName() : "";
        
        return name1.compareToIgnoreCase(name2);
    }
}