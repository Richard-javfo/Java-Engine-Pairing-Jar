/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.api;

/**
 *
 * @author DP
 */
public class Round {
    
    final public static char EMPTY = 'X';
    final public static char PAIRING_ALLOCATED_BYE_RESULT = 'U'; // Das FIDE-'U'
    final public static char  BYE_COLOR = '-'; 
    
    public int roundNr;
    final public int opponentStartRank;
    final public char color;
    public char result;

    public Round(int roundNr,int opponentStartRank, char color, char result) {
        this.roundNr = roundNr;
        this.opponentStartRank = opponentStartRank;
        this.color = color;
        this.result = result;
    }



    

    
    
    // Hilfsmethode für den Generator (genau 8 Zeichen)
    public String toTrfString() {
        // Format: 4 Stellen ID, 1 Stelle Leerzeichen, 1 Stelle Farbe, 1 Stelle Ergebnis
        // Beispiel: "  12 w 1" (insgesamt 8 Zeichen inkl. Padding)
        if(opponentStartRank == 0)
            return String.format("0000 %c %c", 
            color, 
            result);
        
        return String.format("%4d %c %c", 
            opponentStartRank, // 0000 bei Spielfrei/Bye
            color, 
            result
        );
    }
    
    
    // In der Klasse Round oder einer Utility-Klasse
        public  static double resultToPoints(char result) {
        return switch (result) {
        case '1', 'W', 'w', '+', 'F', 'f', 'U', 'u' -> 1.0;
        case '=', 'D', 'd', 'H'                     -> 0.5;
        default                                     -> 0.0;
    };
}
}
