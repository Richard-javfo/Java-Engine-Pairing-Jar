/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package trf.api;

import java.util.List;

/**
 *
 * @author DP
 */
public interface TournamentPlayer {

    public int getActualRank();

    public int getStartRank();

    public String getSex();

    public String getTitle();

    public String getName();

    public int getElo();

    public int getNationalElo();

    public String getFederation();

    public String getFideId();

    public String getNationalId();

    public String getBirthDate();

    public double getPoints();
    
    public void setPoints(double p);

    //public void setPoints(double points);
    default public double calculatePoints() {
//        - forfeit loss
//        + forfeit win
//        The scheduled game lasted less than one move
//        W win Not rated
//        D draw Not rated
//        L loss Not rated
//        Regular game
//        1 win
//        = draw
//        0 loss
//        Bye
//        H half-point-bye Not rated
//        F full-point-bye Not rated
//        U pairing-allocated bye At most once for round - Not rated
//        (U for player unpaired by the system)
//        Z  zero-point-bye Known absence from round - Not rated  

        return this.getRounds().stream()
                .mapToDouble(r -> switch (r.result) {
            case '+', 'W', '1', 'F', 'U', 'w', 'f', 'u' ->
                1.0;
            case 'D', '=', 'H', 'd' ->
                0.5;
            default ->
                0.0;
        })
                .sum();

    }

    public List<Round> getRounds();

    public void setPreviousRank(int actualRank);

    public void setCurrentRound(int currentRound);

    public void setActualRank(int i);

}
