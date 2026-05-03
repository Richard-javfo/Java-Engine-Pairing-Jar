/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import trf.api.Round;
import trf.api.TournamentPlayer;
import trf.impl.TRFJavafoPairingProvider;

/**
 *
 * @author DP
 */
public class TournamentPlayerImpl implements TournamentPlayer{

    //gender title name                       elo country fideId  birthYear
    //m  g Mirzoev Azer                      2527 AZE    13400304 1978
    public final static int NO_START_RANK = -1;
    private double points;
    private int startRank;
    private int actualRank;
    private int previousRank;
    private int currentRound;

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    private String sex;         // 1 Zeichen (m/w)
    private String title;       // 3 Zeichen (z.B. " GM")
    private String name;        // 33 Zeichen
    private int elo;            // 4 Zeichen
    private int nationalElo;
    private String federation;  // 3 Zeichen
    private String fideId;
    private String nationalId;
    private String birthDate;   // 10 Zeichen (YYYY/MM/DD)

    private List<Round> rounds;

    public TournamentPlayerImpl(int rounds) {
        this.rounds = new ArrayList(rounds);
        this.startRank = NO_START_RANK;
    }
    
     public TournamentPlayerImpl(int startRank,String name,int rounds) {
        this.rounds = new ArrayList(rounds);
        this.startRank = startRank;
        this.name = name;
    }
    
    public TournamentPlayerImpl(String spieler_A, int elo, int nationalElo) {
        
        this(7);
        this.elo = elo;
        this.nationalElo = nationalElo;
    }

    public TournamentPlayerImpl() {
    }

    public String getStateString() {
        return name + "aktuelle Platzierung " + actualRank + " Vorherige Platzierung: " + this.previousRank;
    }

    @Override
    public int getActualRank() {
        return actualRank;
    }

    @Override
    public int getStartRank() {
        return startRank;
    }

    @Override
    public String getSex() {
        return sex;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getElo() {
        return elo;
    }

    @Override
    public int getNationalElo() {
        return nationalElo;
    }

    @Override
    public String getFederation() {
        return federation;
    }

    @Override
    public String getFideId() {
        return fideId;
    }

    @Override
    public String getNationalId() {
        return nationalId;
    }

    @Override
    public String getBirthDate() {
        return birthDate;
    }

    

    
   
    
    @Override
    public List<Round> getRounds() {
        return rounds;
    }

    public void setActualRank(int actualRank) {
        this.actualRank = actualRank;
    }

    public void setStartRank(int startRank) {
        this.startRank = startRank;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public void setNationalElo(int nationalElo) {
        this.nationalElo = nationalElo;
    }

    public void setFederation(String federation) {
        this.federation = federation;
    }

    public void setFideId(String fideId) {
        this.fideId = fideId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public int getPreviousRank() {
        return previousRank;
    }

    public void setPreviousRank(int previousRank) {
        this.previousRank = previousRank;
    }

    // Hilfsmethode für den Generator
    static public String getEloAsTrf(int elo) {
        if (elo <= 0) {
            return "   0"; // Leerstellen für Spieler ohne Elo
        }
        return String.format("%4d", elo);
    }

    @Override
    public double getPoints() {
        return this.points;
    }

    @Override
    public void setPoints(double p) {
        this.points = p;
    }

}
