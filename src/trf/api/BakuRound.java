/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package trf.api;

/**
 *
 * @author DP
 */
public record BakuRound(int playerStartRank,int roundNr,  double points) {
    
    public String toString(){
        return "rank " + playerStartRank + "  round " + roundNr + "  " + points;
    }
}
