/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import trf.api.PlayerLoader;
import trf.api.Round;
import trf.impl.JavaTournamentPlayer;

/**
 *
 * @author DP
 */
public class DsbCsvPlayerSource implements PlayerLoader {

    @Override
    public List<JavaTournamentPlayer> loadPlayers(InputStream input, RatingMode mode) {
        List<JavaTournamentPlayer> players = new ArrayList<>();
        // UTF-8 ist wichtig für Umlaute in Spielernamen
        InputStreamReader isr = new InputStreamReader(input);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

        String line;
        boolean isHeader = true;

        try {
            while ((line = reader.readLine()) != null) {

                //System.out.println("Roh-Zeile: " + line);
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (isHeader) {
                    isHeader = false;
                    continue;
                } // Überspringe ID,VKZ...

                // Dieser Regex splittet am Komma, ignoriert aber Kommas in "..."
                String[] cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (cols.length >= 15) {
                    JavaTournamentPlayer p = new JavaTournamentPlayer("Spieler A", 2000, 1800);

                    p.setNationalId(cols[0].trim());

                    // Name: Entfernt Anführungszeichen (Index 4)
                    p.setName(cols[4].replace("\"", "").trim());
                    p.setSex(cols[5].trim());

                    // Geburtsjahr (Index 7) -> Format für TRF: YYYY/00/00
                    p.setBirthDate(cols[7].trim() + "/00/00");

                    // DWZ
                    p.setNationalElo(parseSafeInt(cols[9]));

                    // FIDE-Elo (Index 11)
                    p.setElo(parseSafeInt(cols[11]));

                    // FIDE-Titel (Index 12)
                    p.setTitle(cols[12].trim().isEmpty() ? "   " : cols[12].trim());

                    // FIDE-ID (Index 13)
                    p.setFideId(cols[13].trim());

                    // Land (Index 14)
                    p.setFederation(cols[14].trim());

                    //p.setRounds(generateDummyRounds());
                    players.add(p);
                }
            }
        } catch (IOException ex) {
            // Gibt den Klassennamen und die Fehlermeldung aus
            System.out.println("ERROR in TRFJavafoPairingProvider: " + ex.getMessage());
            // Druckt den kompletten Stacktrace in das Logcat/Konsole (sehr wichtig für die Fehlersuche!)
            ex.printStackTrace();
        }

        return players;
    }

    private int parseSafeInt(String val) {
        if (val == null) {
            return 0;
        }

        // 1. Alle Leerzeichen entfernen (auch die "festen" von Excel)
        String clean = val.trim().replace("\u00a0", "").replace(" ", "");

        // 2. Tausender-Punkte entfernen (aus 2.250 wird 2250)
        clean = clean.replace(".", "");

        // 3. Falls ein Komma drin ist (2250,00), nur den Teil davor nehmen
        if (clean.contains(",")) {
            clean = clean.split(",")[0];
        }

        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
