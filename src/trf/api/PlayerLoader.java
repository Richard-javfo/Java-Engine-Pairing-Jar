/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package trf.api;

import java.io.IOException;
import trf.impl.TournamentPlayerImpl;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author DP
 */
public interface PlayerLoader {
    enum RatingMode {
        FIDE_ONLY, 
        NATIONAL_ONLY, 
        BOTH_FIDE_FIRST, 
        BOTH_NATIONAL_FIRST
    }

    // Die Haupt-Methode, die jeder Loader implementieren MUSS
    List<TournamentPlayerImpl> loadPlayers(InputStream input, RatingMode mode);

    // Die Komfort-Methode: Ruft automatisch FIDE_ONLY auf
    default List<TournamentPlayerImpl> loadPlayers(InputStream input) {
        return loadPlayers(input, RatingMode.FIDE_ONLY);
    }
    
    public static void ensureUtf8(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] content = Files.readAllBytes(path);

        // Prüfen, ob die Datei bereits UTF-8 ist (einfacher Check auf "Müller"-Umlaute)
        if (isAlreadyUtf8(content)) {
            System.out.println("Datei ist bereits UTF-8. Keine Konvertierung nötig.");
            return;
        }

        // Wenn nicht, dann als Windows-1252 (ANSI) interpretieren und konvertieren
        String decoded = new String(content, Charset.forName("Windows-1252"));
        Files.write(path, decoded.getBytes(StandardCharsets.UTF_8));
        System.out.println("Erfolgreich von ANSI zu UTF-8 konvertiert.");
    }
    
    public static boolean isAlreadyUtf8(byte[] bytes) {
        // Ein sehr simpler Check: UTF-8 nutzt für Umlaute wie 'ü' zwei Bytes.
        // Wir schauen, ob die Byte-Sequenz für 'ü' (0xC3 0xBC) vorkommt.
        for (int i = 0; i < bytes.length - 1; i++) {
            if ((bytes[i] & 0xFF) == 0xC3 && (bytes[i + 1] & 0xFF) == 0xBC) {
                return true;
            }
        }
        return false;
    }

}
