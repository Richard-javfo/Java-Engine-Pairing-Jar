/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package trf.tiebreak;

import java.util.List;
import java.util.Map;
import trf.api.Round;
import trf.api.TournamentPlayer;


/**
 *
 * @author DP
 */
public interface TiebreakStrategy {

    
    String getLabel();
    
    
    // Dein "Paket" direkt im Interface
    record OpponentData(Round round, double opponentPoints) {}

  
    List<OpponentData> getSortedOpponentData(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers,int totalRounds);
    
    double calculate(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers,int totalrounds);
}