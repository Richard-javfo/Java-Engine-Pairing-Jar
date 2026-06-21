package trf.api;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
/**
 *
 * @author DP
 */
public enum ChessResult {
    // Standard
    WHITE_WIN('1'),
    DRAW('='),
    BLACK_WIN('0'),
    // Forfeit / Kampflos
    FORFEIT_WHITE_WIN('+'),
    FORFEIT_BLACK_WIN('-'),
    BOTH_FORFEIT('-'),
    // Not Rated
    WHITE_WIN_NOT_RATED('W'),
    DRAW_NOT_RATED('D'),
    BLACK_WIN_NOT_RATED('L'),
    // Sonstige
    PAIRING_ALLOCATED_BYE('U'),
    HALF_POINT_BYE('H'),
    FULL_POINT_BYE('F'),
    ZERO_POINT_BYE('Z'),
    ZERO_POINT_BYE_UNTIL_END('Z'),
    OPEN('.'),;

    private final char trfChar;

    ChessResult(char trfChar) {
        this.trfChar = trfChar;
    }

    public char getTrfChar() {
        return trfChar;
    }

    public Boolean isBye() {
        return switch (this) {
            case ZERO_POINT_BYE_UNTIL_END ->
                true;
            case ZERO_POINT_BYE ->
                true;
            case HALF_POINT_BYE ->
                true;
            case FULL_POINT_BYE ->
                true;
            case PAIRING_ALLOCATED_BYE ->
                true;

            default ->
                false;
        };
    }

    /**
     * Liefert das TRF-Ergebnis aus der Sicht von Schwarz.
     */
    public String blackTrfResult() {
        return switch (this) {
            case WHITE_WIN ->
                "0";
            case BLACK_WIN ->
                "1";
            case DRAW ->
                "=";
            case FORFEIT_WHITE_WIN ->
                "-";
            case FORFEIT_BLACK_WIN ->
                "+";
            case WHITE_WIN_NOT_RATED ->
                "L";
            case BLACK_WIN_NOT_RATED ->
                "W";
            case PAIRING_ALLOCATED_BYE ->
                "U";
            case HALF_POINT_BYE ->
                "H";
            case FULL_POINT_BYE ->
                "F";
            case ZERO_POINT_BYE ->
                "Z";
            case DRAW_NOT_RATED ->
                "D";
            default ->
                ".";
        };
    }

    /**
     * Konvertiert den TRF-Ergebnisstring je nach Farbe in ein ChessResult.
     */
    public static ChessResult trf2chessResult(String trfResultString, String color) {
        if (trfResultString == null || color == null) {
            return ChessResult.OPEN;
        }

        boolean isWhite = color.equalsIgnoreCase("w");

        return switch (trfResultString.toUpperCase()) {
            case "1" ->
                isWhite ? ChessResult.WHITE_WIN : ChessResult.BLACK_WIN;
            case "0" ->
                isWhite ? ChessResult.BLACK_WIN : ChessResult.WHITE_WIN;
            case "=" ->
                ChessResult.DRAW;
            case "U" ->
                ChessResult.PAIRING_ALLOCATED_BYE;
            case "H" ->
                ChessResult.HALF_POINT_BYE;
            case "F" ->
                ChessResult.FULL_POINT_BYE;
            case "Z" ->
                ChessResult.ZERO_POINT_BYE;
            case "+" ->
                isWhite ? ChessResult.FORFEIT_WHITE_WIN : ChessResult.FORFEIT_BLACK_WIN;
            case "-" ->
                isWhite ? ChessResult.FORFEIT_BLACK_WIN : ChessResult.FORFEIT_WHITE_WIN;
            case "W" ->
                isWhite ? ChessResult.WHITE_WIN_NOT_RATED : ChessResult.BLACK_WIN_NOT_RATED;
            case "L" ->
                isWhite ? ChessResult.BLACK_WIN_NOT_RATED : ChessResult.WHITE_WIN_NOT_RATED;
            case "D" ->
                ChessResult.DRAW_NOT_RATED;
            default ->
                ChessResult.OPEN;
        };
    }

    /**
     * Prüft, ob aus diesem Zustand heraus ein Paarungs-Status generiert werden
     * kann.
     */
    public static boolean canBuildPairingState(String trfResultString, String color) {
        if (color == null || color.equalsIgnoreCase("b")) {
            return false;
        }

        ChessResult result = trf2chessResult(trfResultString, color);

        return switch (result) {
            case BOTH_FORFEIT, FULL_POINT_BYE, ZERO_POINT_BYE, HALF_POINT_BYE, OPEN ->
                false;
            default ->
                true;
        };
    }

    public static boolean isPairingState(char trfResult) {
        return switch (trfResult) {
            // Hier direkt die echten Zeichen eintragen:
            case 'F', 'H', 'Z', 'f', 'h', 'z', '.' ->
                false;
            default ->
                true;
        };
    }

}
