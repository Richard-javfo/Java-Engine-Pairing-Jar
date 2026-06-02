/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package trf.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import trf.api.Round;
import trf.api.TournamentInfo;
import trf.api.TournamentPlayer;
import trf.api.TournamentState;
import trf.impl.TournamentInfoImpl;
import trf.impl.JavaTournamentPlayer;

/**
 *
 * @author DP
 */
public interface TrfParser {

    static TournamentState parse(InputStream inputStream) {

        Map<Integer, TournamentPlayer> players = new HashMap<>();
        TournamentInfo info = new TournamentInfoImpl(players);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                // SCHNELL-CHECK: Spieler oder Header?
                if (line.startsWith("001")) {
                    TournamentPlayer p = parseLineToPlayer(line);
                    if (p != null) {
                        players.put(p.getStartRank(), p);
                    }
                } else {
                    // Alles andere geht an die Info-Logik (012, XXR, etc.)
                    ((TournamentInfoImpl) info).parseHeaderLine(line);
                }
            }
        } catch (IOException e) {
            // Fehlerbehandlung
        }

        TournamentState tournamentState = new TournamentState(info);

        return validateRound(tournamentState, players);
    }

    static TournamentState validateRound(TournamentState tournamentState, Map<Integer, TournamentPlayer> players) {
        Boolean isTournamentStateRundNrSet = false;
        for (TournamentPlayer p : players.values()) {

            if (p.getRounds() == null || p.getRounds().size() == 0) {
                return tournamentState;
            }
            Round lastRound = p.getRounds().get(p.getRounds().size() - 1);

            if (!isTournamentStateRundNrSet) {
                if (lastRound.result == '1' || lastRound.result == '=' || lastRound.result == '0') {
                    tournamentState.setCurrentRound(p.getRounds().size());
                    isTournamentStateRundNrSet = true;
                }
            }
            for(int i=0; i< p.getRounds().size();i++)
                p.getRounds().get(i).roundNr=i+1;
            
//tournamentState.getInfo().getTournamentPlayers().put(p.getStartRank(), p);
           
        }
        
        return tournamentState;
    }

    private static TournamentPlayer parseLineToPlayer(String line) {
        JavaTournamentPlayer player = new JavaTournamentPlayer();

        // 001 steht an 0-2, wir fangen bei der Startnummer an
        player.setStartRank(parseSafeInt(line, 4, 8));    // 5-8 (Index 4-8)
        player.setSex(substringSafe(line, 9, 10));       // 10 (Index 9)
        player.setTitle(substringSafe(line, 10, 13));    // 11-13 (Index 10-13)
        player.setName(substringSafe(line, 14, 47));     // 15-47 (Index 14-47)

        // Ab hier wird es kritisch in deinem File - wir nutzen Trimming
        player.setElo(parseSafeInt(line, 48, 52));       // 49-52
        player.setFederation(substringSafe(line, 53, 56)); // 54-56

        // FIDE-ID (58-68 -> Index 57-68)
        player.setFideId(substringSafe(line, 57, 68));

        // Geburtsdatum (70-79 -> Index 69-79)
        player.setBirthDate(substringSafe(line, 69, 79));

        // Punkte (81-84) & Rank (86-89)
        player.setActualRank(parseSafeInt(line, 85, 89));    // 5-8 (Index 4-8)
        // Diese können wir parsen, aber für deine Logik 
        // berechnest du die Punkte ja ohnehin aus den Runden neu.
        // Runden ab Spalte 92 (Index 91)
        player.setRounds(parseRounds(line));

        player.calculatePoints();

        return player;
    }

    private static List<Round> parseRounds(String line) {
        List<Round> rounds = new ArrayList<>();

        // Wir starten ab Spalte 92 (Index 91) laut Spec
        if (line.length() <= 91) {
            return rounds;
        }

        // Wir extrahieren den Rest der Zeile und teilen ihn in Tokens auf
        String[] tokens = line.substring(91).trim().split("\\s+");

        int roundNr = 0;
        int i = 0;
        while (i + 2 < tokens.length) {
            String idToken = tokens[i];
            String colorToken = tokens[i + 1];
            String resultToken = tokens[i + 2];

            // Die ID bestimmen: "0000" wird zu 0, sonst die Zahl parsen
            int opponentId = idToken.equals("0000") ? 0 : Integer.parseInt(idToken);

            char color = colorToken.charAt(0);
            char result = resultToken.charAt(0);

            // Neues Round-Objekt hinzufügen
            rounds.add(new Round(roundNr, opponentId, color, result));
            roundNr++;
            // Zum nächsten Triple springen (3er Schritt)
            i += 3;
        }
        return rounds;
    }

    // Hilfsmethoden für sauberes Parsing
    private static String substringSafe(String line, int start, int end) {
        if (line.length() <= start) {
            return "";
        }
        return line.substring(start, Math.min(end, line.length())).trim();
    }

    private static int parseSafeInt(String line, int start, int end) {
        String val = substringSafe(line, start, end);
        try {
            return val.isEmpty() ? 0 : Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
