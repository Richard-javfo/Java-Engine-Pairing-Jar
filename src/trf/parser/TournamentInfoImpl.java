/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.parser;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import trf.api.BakuRound;
import trf.api.TournamentInfo;
import trf.api.TournamentPlayer;
import trf.tiebreak.TiebreakStrategy;

/**
 *
 * @author DP
 */
public class TournamentInfoImpl implements TournamentInfo {

   public  String tournamentName;
   public  String city;
   public  String federation;
   public  LocalDate startDate;
   public  LocalDate endDate;
   public  String type;
   public  String chiefArbiter;
   public  String deputyArbiter;
   public  String timeControl;
   public  int totalRounds;
   public  List<String> engineConfigs = new ArrayList<>();
 
   public  final List<LocalDate> roundDates = new ArrayList<>();
   public  final List<String> forbiddenPairs = new ArrayList<>();
   public  final List<TiebreakStrategy> tiebreakStrategies = new ArrayList<>();
   public  String extraOptions = "";
   public  Map<Integer, TournamentPlayer> players;
   public  Map<Integer, List<BakuRound>> bakuRounds = new HashMap<>();

    public TournamentInfoImpl(Map<Integer, TournamentPlayer> players) {
        this.players = players;
    }


    // --- Getter Implementierungen ---
    @Override
    public String getTournamentName() {
        return tournamentName;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getFederation() {
        return federation;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getChiefArbiter() {
        return chiefArbiter;
    }

    @Override
    public String getDeputyArbiter() {
        return deputyArbiter;
    }

    @Override
    public String getTimeControl() {
        return timeControl;
    }

    @Override
    public int getTotalRounds() {
        return totalRounds;
    }

    @Override
    public List<LocalDate> getRoundDates() {
        return roundDates;
    }

    @Override
    public List<String>getEngineConfigsXXC() {
        return engineConfigs;
    }

    @Override
    public List<String> getForbiddenPairs() {
        return forbiddenPairs;
    }

    @Override
    public String getExtraOptions() {
        return extraOptions;
    }

    @Override
    public String getTournamentID() {
       return "TRF-Turnier-Import-"+OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    @Override
    public List<TiebreakStrategy> listTiebreaksStrategies() {

        return tiebreakStrategies;
    }

    @Override
    public Map<Integer, TournamentPlayer> getTournamentPlayers() {
        return players;
    }

    @Override
    public Map<Integer, List<BakuRound>> getBakuRounds() {
       
        return bakuRounds;

    }
        
}
