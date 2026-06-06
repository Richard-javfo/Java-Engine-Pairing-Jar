/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.api;

import java.time.LocalDate;
import trf.impl.JavaTournamentPlayer;
import java.util.List;
import java.util.Map;
import trf.tiebreak.TiebreakStrategy;


/**
 *
 * @author DP
 */
    public interface TournamentInfo {

        String getTournamentID();
        // Basis-Daten für die Steuerung
        int getTotalRounds();              //XXR 7  Bedeuted im TRF 7 Runden

        String getExtraOptions();

        // TRF Header-Daten (012 - 132)
        String getTournamentName();       // 012
        String getCity();                 // 022
        String getFederation();           // 032 (z.B. GER)
        LocalDate getStartDate();            // 042 (Format: YYYY/MM/DD)
        LocalDate getEndDate();              // 052
        //int getNumPlayers();              // 062    kann beechnet werden
        //int getNumRatedPlayers();         // 072
        //int getNumTeams();                // 082 (0 wenn Einzelturnier)
        String getType();                 // 092 (z.B. Swiss System)
        String getChiefArbiter();         // 102
        String getDeputyArbiter();        // 112
        String getTimeControl();          // 122 (z.B. 90 min + 30 s)
        List<LocalDate> getRoundDates();           // 132
        FirstRoundColor getFirstRoundColor(); // e.g. XXC black1
        List<String> getForbiddenPairs();   // XXP 32 14
        //void addForbiddenPair(String pair);
        List<TiebreakStrategy> listTiebreaksStrategies();
        //public void parseHeaderLine(String line);
        Map<Integer,TournamentPlayer> getTournamentPlayers();

    }

