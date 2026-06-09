/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package trf.api;

/**
 *
 * @author DP
 */
public enum BakuStrategy {
    
    PERCENT_STEPS("Baku-System (Prozentuall)", "XXA") {
        @Override
        public double calculatePoints(int rank, int totalPlayers, int round) {
            double percentile = (double) rank / totalPlayers;

            if (round == 1) {
                if (percentile <= 0.10) return 2.0;
                if (percentile <= 0.30) return 1.5;
                if (percentile <= 0.50) return 1.0;
                return 0.0;
            }
            if (round == 2 || round == 3) {
                if (percentile <= 0.25) return 0.5;
                return 0.0;
            }
            return 0.0;
        }
    },
    
    CLASSICAL_BAKU("Baku-System (Offiziell)", "XXA") {
        @Override
        public double calculatePoints(int rank, int totalPlayers, int round) {
            int half = (int) Math.ceil(totalPlayers / 2.0);
            if (rank > half) return 0.0;
            return (round == 1) ? 1.0 : (round <= 3 ? 0.5 : 0.0);
        }
    },
    
    DISABLED("Deaktiviert / Normal", "") {
        @Override
        public double calculatePoints(int rank, int totalPlayers, int round) {
            return 0.0;
        }
    };

    // Zentrale Felder für alle Strategien
    private final String displayName;
    private final String trfCode;

    // Konstruktor für das Enum
    BakuStrategy(String displayName, String trfCode) {
        this.displayName = displayName;
        this.trfCode = trfCode;
    }

    // Öffentliche Getter, die du in Kotlin nutzen kannst
    public String getDisplayName() { return displayName; }
    public String getTrfCode() { return trfCode; }

    // Die abstrakte Methode bleibt natürlich bestehen
    public abstract double calculatePoints(int rank, int totalPlayers, int round);
    
    
}
