/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package trf.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author DP
 */
public interface FlexibleDateParser {

    final DateTimeFormatter TRF_DATE_SHORT = DateTimeFormatter.ofPattern("yy/MM/dd");
    final DateTimeFormatter TRF_DATE_LONG = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    final DateTimeFormatter[] DATE_FORMATTERS = {
        DateTimeFormatter.ofPattern("dd.MM.yyyy"), // 05.06.2026
        DateTimeFormatter.ofPattern("d.M.yyyy"), // 5.6.2026
        DateTimeFormatter.ofPattern("dd.MM.yy"), // 05.06.26
        DateTimeFormatter.ofPattern("d.M.yy"), // 5.6.26
        DateTimeFormatter.ISO_DATE, // 2026-06-05 (ISO-Standard)
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("yyyy/MM/dd"),
        TRF_DATE_LONG,
        TRF_DATE_SHORT};

    final DateTimeFormatter[] TRF_SHORT_DATE_FORMATTERS = {
        TRF_DATE_SHORT, DateTimeFormatter.ofPattern("yy-MM-dd"),
        DateTimeFormatter.ofPattern("yy MM dd")};

    static LocalDate parse(String dateString) {
        String cleanedString = dateString.trim();
        if (cleanedString.isEmpty()) {
            return null;
        }

        // Jedes Format nacheinander ausprobieren
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(cleanedString, formatter);
            } catch (DateTimeParseException e) {
                // Dieses Format passte nicht, probiere das nächste im Loop
            }
        }

        // Wenn kein Format gepasst hat
        return null;
    }

    static LocalDate parseShort(String dateString) {
        String cleanedString = dateString.trim();
        if (cleanedString.isEmpty()) {
            return null;
        }

        // Jedes Format nacheinander ausprobieren
        for (DateTimeFormatter formatter : TRF_SHORT_DATE_FORMATTERS) {
            try {
                return LocalDate.parse(cleanedString, formatter);
            } catch (DateTimeParseException e) {
                // Dieses Format passte nicht, probiere das nächste im Loop
            }
        }

        // Wenn kein Format gepasst hat
        return null;
    }

}
