/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package trf.impl.tiebreak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

  
    List<OpponentData> getSortedOpponentData(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers,int totalRounds,int actualRounds);
    double calculate(TournamentPlayer p, Map<Integer, TournamentPlayer> allPlayers,int totalrounds,int actualRoung);
}