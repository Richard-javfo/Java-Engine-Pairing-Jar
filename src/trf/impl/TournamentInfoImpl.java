/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.impl;

import static java.time.LocalDate.now;
import static java.time.LocalDateTime.now;
import java.time.OffsetDateTime;
import static java.time.OffsetDateTime.now;
import static java.time.OffsetTime.now;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import trf.api.FirstRoundColor;
import trf.api.TournamentInfo;
import trf.api.TournamentPlayer;
import trf.tiebreak.TiebreakStrategy;

/**
 *
 * @author DP
 */
public class TournamentInfoImpl implements TournamentInfo {

    private String tournamentName;
    private String city;
    private String federation;
    private String startDate;
    private String endDate;
    private String type;
    private String chiefArbiter;
    private String deputyArbiter;
    private String timeControl;
    private int totalRounds;
    private FirstRoundColor firstRoundColor = FirstRoundColor.RANDOM;
    private final List<String> roundDates = new ArrayList<>();
    private final List<String> forbiddenPairs = new ArrayList<>();
    private final List<TiebreakStrategy> tiebreakStrategies = new ArrayList<>();
    private String extraOptions = "";
    private Map<Integer, TournamentPlayer> players;

    public TournamentInfoImpl(Map<Integer, TournamentPlayer> players) {
        this.players = players;
    }

    public void parseHeaderLine(String line) {
        if (line.length() < 4) {
            return;
        }

        String tag = line.substring(0, 3);
        // Die eigentlichen Daten beginnen im TRF meist ab Position 14
        String content = line.length() > 4 ? line.substring(3).trim() : "";

        switch (tag) {
            case "012" ->
                this.tournamentName = content;
            case "022" ->
                this.city = content;
            case "032" ->
                this.federation = content;
            case "042" ->
                this.startDate = content;
            case "052" ->
                this.endDate = content;
            case "092" ->
                this.type = content;
            case "102" ->
                this.chiefArbiter = content;
            case "112" ->
                this.deputyArbiter = content;
            case "122" ->
                this.timeControl = content;
            case "132" ->
                this.roundDates.add(content); // Kann mehrfach vorkommen
            case "XXR" ->
                this.totalRounds = Integer.parseInt(content);
            case "XXC" ->
                this.firstRoundColor = parseColor(content);
            case "XXP" ->
                this.forbiddenPairs.add(content);
            case "XXO" ->
                this.extraOptions = content; // Falls vorhanden
        }
    }

    private FirstRoundColor parseColor(String content) {
        if (content.toLowerCase().contains("white1")) {
            return FirstRoundColor.WHITE;
        }
        if (content.toLowerCase().contains("black1")) {
            return FirstRoundColor.BLACK;
        }
        return FirstRoundColor.RANDOM;
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
    public String getStartDate() {
        return startDate;
    }

    @Override
    public String getEndDate() {
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
    public List<String> getRoundDates() {
        return roundDates;
    }

    @Override
    public FirstRoundColor getFirstRoundColor() {
        return firstRoundColor;
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
       return "TRF-Turnier-Import"+OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    @Override
    public List<TiebreakStrategy> listTiebreaksStrategies() {

        return tiebreakStrategies;
    }

    @Override
    public Map<Integer, TournamentPlayer> getTournamentPlayers() {
        return players;
    }
}
