/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.api;

import trf.impl.JavaTournamentPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author DP
 */
public record Pairing(Round whiteRound, int whitePlayerStartNo,
        Round blackRound, int blackPlayerStartNo) {
    
   public Pairing withWhiteRound(Round whiteRound){
       return new Pairing(whiteRound,this.whitePlayerStartNo,this.blackRound,this.blackPlayerStartNo);
   }
   
   public Pairing withBlackRound(Round blackRound){
       return new Pairing(this.whiteRound,this.whitePlayerStartNo,blackRound,this.blackPlayerStartNo);
   }
    
    
    public String pairStr(){
        
        return whitePlayerStartNo + " " + blackPlayerStartNo;
    }
    public String pairStr(Map<Integer, TournamentPlayer> players){
        
        String s =String.format(" %4d",whitePlayerStartNo) + " " + String.format("%-" + 33 + "s",players.get(whitePlayerStartNo).getName());
                  String blackName = blackPlayerStartNo==0?" kampflos":  players.get(blackPlayerStartNo).getName();
                  s+= " - "+String.format(" %4d",blackPlayerStartNo) + " " + String.format("%-" + 33 + "s",blackName);
        
        return s;
    }
    


    public static List<Pairing> parseJavaFoToPairings(String javafoOutput) {
        List<Pairing> pairings = new ArrayList<>();
        Scanner scanner = new Scanner(javafoOutput);

        if (!scanner.hasNextInt()) {
            return pairings;
        }

        int count = scanner.nextInt(); // Die erste Zahl (z.B. 25)

        for (int i = 0; i < count; i++) {
            if (!scanner.hasNextInt()) {
                break;
            }

            int wNo = scanner.nextInt();
            int bNo = scanner.nextInt();
            
            //(int roundNr,int opponentStartRank, char color, char result)
            
            // 1. Runde für den weißen Spieler: Gegner ist bNo, Farbe 'W'
            Round whiteRound = (bNo == 0) ? new Round(i,0,Round.BYE_COLOR,Round.PAIRING_ALLOCATED_BYE_RESULT) :new Round(i,bNo, 'w','X');

            // 2. Runde für den schwarzen Spieler: Gegner ist wNo, Farbe 'b' (oder 'B' für Black)
            // Falls bNo == 0 (Spielfrei), existiert keine schwarze Runde
            Round blackRound = (bNo == 0) ? null : new Round(i,wNo, 'b','X');

            pairings.add(new Pairing(whiteRound, wNo, blackRound, bNo));
        }

        return pairings;
    }
}
