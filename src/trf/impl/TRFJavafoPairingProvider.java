/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javafo.api.JaVaFoApi;
import trf.api.AbstractPairingProvider;
import trf.api.BakuRound;
import trf.api.Pairing;
import trf.api.Round;
import trf.api.TournamentInfo;
import trf.api.TournamentState;
import trf.api.PairingListener;
import trf.api.TournamentPlayer;
import trf.parser.FlexibleDateParser;

/**
 * //
 *
 *
 * @author DP
 */
public class TRFJavafoPairingProvider extends AbstractPairingProvider {

    private TournamentState tournamentState;
    private TournamentInfo tournamentInfo;

//    public TRFJavafoPairingProvider(TournamentInfo tournamentInfo, TournamentState tournamentState, PairingListener listener) {
//        this.tournamentInfo = tournamentInfo;
//        this.tournamentState = tournamentState;
//        this.listener = listener;
//    }
    public TRFJavafoPairingProvider(TournamentInfo tournamentInfo) {

        this.tournamentInfo = tournamentInfo;
        this.tournamentState = new TournamentState(tournamentInfo);
    }

    public List<TournamentPlayer> getTournamentPlayers() {

        return new ArrayList<>(this.tournamentInfo.getTournamentPlayers().values());
    }

    public List<List<BakuRound>> getBakuRounds() {

        return new ArrayList<>(this.tournamentInfo.getBakuRounds().values());
    }

    private void writeHeader(PrintWriter writer, TournamentInfo ti) throws Exception {
        writeHeader(writer, ti, null);
    }

    private void writeHeader(PrintWriter writer, TournamentInfo ti, List<TournamentPlayer> players) throws Exception {

        if (players != null) {

            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            // 012 - Turniername 
            writer.println("012 " + ti.getTournamentName());
            writer.println("022 " + ti.getCity());
            writer.println("032 " + ti.getFederation());

            // 042 & 052 - Datum (Automatisch, wenn Map leer)
            writer.println("042 " + ti.getStartDate());
            writer.println("052 " + ti.getEndDate());

            // 062 & 072 - Spielerzahlen (Immer live aus der Liste berechnet)
            writer.println("062 " + getTournamentPlayers().size());
            writer.println("072 " + countRated(getTournamentPlayers()));

            writer.println("082 0");
            writer.println("092 " + ti.getType());
            writer.println("102 " + ti.getChiefArbiter());
            writer.println("112 " + ti.getDeputyArbiter());
            writer.println("122 " + ti.getTimeControl());

            final StringBuilder sb = new StringBuilder(91);
            sb.append("132 ");
            // 1. Padding bis Position 91 (Index 90, da 0-basiert)
            // Da wir bei Index 4 (nach "132 ") sind, füllen wir auf:
            while (sb.length() < 91) {
                sb.append(" ");
            }
            if (ti.getRoundDates() != null) {
                for (LocalDate rundDate : ti.getRoundDates()) {

                    sb.append(rundDate.format(FlexibleDateParser.TRF_DATE_SHORT));
                    sb.append("  ");
                }
            }

            writer.println(sb.toString());
        } else {
            throw new Exception("no player for engine");
        }
        // XXR - Die wichtigste Zeile für Javafo
        writer.println("XXR " + ti.getTotalRounds());

        for (String c : ti.getEngineConfigsXXC()) {

            writer.println("XXC " + c);

        }
        for (String pairStr : ti.getForbiddenPairs()) {
            writer.println("XXP " + pairStr);
        }

    }

    private void writeTrfLine(TournamentPlayer p, PrintWriter writer) {

        StringBuilder sb = new StringBuilder(100 + p.getRounds().size() * 10);
        appendPlayerLine001(p, sb);
        //sb.append("\r\n");
        //appendPlayerLine002(p,sb);

        // In den Writer schreiben
        writer.print(sb.toString());
        
    }

    private void writeTrfLine(List<BakuRound> playersBakus, PrintWriter writer) {

        if (playersBakus.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder(12 + playersBakus.size() * 5);
        sb.append("XXA");

        sb.append(String.format(" %4d", playersBakus.getFirst().playerStartRank()));

        for (BakuRound bakuRound : playersBakus) {
            sb.append("  ");

            sb.append(String.format(Locale.US, "%2.1f", bakuRound.points()));

        }
        writer.print(sb.toString());
        

    }

    // In deinem Generator-JAR
    @Override
    public void generateInitialTrf(OutputStream out) {
        try {

            List<TournamentPlayer> players = this.getTournamentPlayers();
            if (players == null || players.size() == 0) {
                throw new Exception("no player in java engine");
            }

            //players.sort(new EloComparator());
            this.tournamentState.updateRanking();

            // Wir bleiben bei UTF-8, aber wir kontrollieren die Byte-Breite manuell
            OutputStreamWriter osw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            PrintWriter writer = new PrintWriter(osw);

            writeHeader(writer, this.tournamentState.getInfo(), players);
            //writeHeader(writer, tournamentInfo);

            for (int i = 0; i < players.size(); i++) {
                TournamentPlayer p = players.get(i);
                //p.setActualRank(i + 1);
//                if (p.getStartRank() == JavaTournamentPlayer.NO_START_RANK) {
//                    p.setStartRank(p.getActualRank());
//                }
                p.calculatePoints();
                writeTrfLine(p, writer);

                writer.print("\r\n");

            }
            List<List<BakuRound>> bakus = this.getBakuRounds();
            for (List<BakuRound> playersBakus : bakus) {
                
                writeTrfLine(playersBakus, writer);
                writer.print("\r\n");
            }

            writer.flush();
        } catch (Exception ex) {
            // Gibt den Klassennamen und die Fehlermeldung aus
            System.out.println("ERROR in TRFJavafoPairingProvider: " + ex.getMessage());
            // Druckt den kompletten Stacktrace in das Logcat/Konsole (sehr wichtig für die Fehlersuche!)
            ex.printStackTrace();
            this.notifyError("ERROR in TRFJavafoPairingProvider: " + ex.getMessage(), ex.toString());
        }
    }

    private String countRated(List<TournamentPlayer> players) {

        int counter = 0;

        for (TournamentPlayer p : players) {
            if (p.getElo() > 0) {
                counter++;
            }
        }

        return String.valueOf(counter);
    }

    @Override
    //public List<Pairing> execPairing(TournamentInfo tournamentInfo, List<TournamentPlayer> players, int currentRound, String engineOptions, PairingListener callback) {
    public List<Pairing> execPairing(String engineOptions) {
        ByteArrayOutputStream outputStream;
        byte[] dataInRam = null;
        ByteArrayInputStream inputStream = null;
        ByteArrayOutputStream pairingOutputStream = null;

        String result = null;

        try {

            // 1. Der RAM-Speicher (Schreib-Seite)
            outputStream = new ByteArrayOutputStream();

            // 2. Daten generieren (mit deinem Generator)
            this.generateInitialTrf(outputStream);

            // 3. Umwandlung: Den Puffer in ein Byte-Array "einfrieren"
            dataInRam = outputStream.toByteArray();

            // 4. Der RAM-Speicher (Lese-Seite für JaVaFo)
            inputStream = new ByteArrayInputStream(dataInRam);

            // 5. JaVaFo füttern
            pairingOutputStream = new ByteArrayOutputStream();
            int option;
            try {
                option = Integer.parseInt(engineOptions);
            } catch (Exception e) {
                option = 1000;
            }

            System.out.println("javafo engine Option = " + option);
            JaVaFoApi.exec(option, this.tournamentInfo.getTournamentName(), inputStream, pairingOutputStream);

            // 6. Ergebnis direkt aus dem RAM lesen
            result = pairingOutputStream.toString();

        } catch (Exception ex) {
            // Gibt den Klassennamen und die Fehlermeldung aus

            // Druckt den kompletten Stacktrace in das Logcat/Konsole (sehr wichtig für die Fehlersuche!)
            ex.printStackTrace();
            // Wir nutzen dataInRam, weil das die stabilen Rohdaten sind, 
            // bevor JaVaFo den Stream "leergesaugt" hat.
            String inputDump = (dataInRam != null) ? new String(dataInRam, StandardCharsets.UTF_8) : "Keine Daten generiert";
            int size = (dataInRam != null) ? dataInRam.length : 0;

            this.listener.onError(ex.getMessage(), "Input Data Size = " + size
                    + "\nContent: " + inputDump);
        }

        return Pairing.parseJavaFoToPairings(result);
    }

    private void appendPlayerLine001(TournamentPlayer p, StringBuilder sb) {
        // Pos 1-3: ID
        sb.append("001");

        // Pos 5-8: Starting Rank
        sb.append(String.format(" %4d", p.getStartRank()));

        // Pos 10: Sex
        sb.append(String.format(" %1s", (p.getSex() == null ? " " : p.getSex())));

        // Pos 11-13: Title
        sb.append(String.format("%3s", (p.getTitle() == null ? "" : p.getTitle())));

        // Pos 15-47 (+ Shift): Name
        sb.append(" "); // Leerzeichen für Pos 14
        sb.append(String.format("%-" + 33 + "s", p.getName()));

        // Pos 49-52: FIDE Rating
        sb.append(" "); // Leerzeichen für Pos 48
        sb.append(String.format("%4d", p.getElo()));

        // Pos 54-56: Federation
        sb.append(" "); // Leerzeichen für Pos 53
        sb.append(String.format("%-3s", (p.getFederation() == null ? "" : p.getFederation())));

        // Pos 58-68: FIDE Number
        sb.append(" "); // Leerzeichen für Pos 57
        sb.append(String.format("%11s", p.getFideId()));

        // Pos 70-79: Birth Date (YYYY/MM/DD)
        sb.append(" "); // Leerzeichen für Pos 69
        sb.append(String.format("%-10s", (p.getBirthDate() == null ? "          " : p.getBirthDate())));

        // Pos 81-84: Points (Format 11.5)
        sb.append(" "); // Leerzeichen für Pos 80
        sb.append(String.format(Locale.US, "%4.1f", p.getPoints()));

        // Pos 86-89: Rank
        sb.append(" "); // Leerzeichen für Pos 85
        sb.append(String.format("%4d", p.getActualRank()));

        if (!p.getRounds().isEmpty()) {

            for (Round round : p.getRounds()) {
                sb.append("  ");
                sb.append(round.toTrfString());

            }

        }

    }

    private void appendPlayerLine002(JavaTournamentPlayer p, StringBuilder sb) {
        if (!p.getRounds().isEmpty()) {
            // "%4d %c %c"

            sb.append("002");
            sb.append("  B1" + String.format(Locale.US, "%4.1f", 0.0));
            sb.append("  ");
            sb.append("  B2" + String.format(Locale.US, "%4.1f", 0.0));
        }
    }

    @Override
    public void fill(List<Pairing> pairingResults, Map<Integer, TournamentPlayer> players) {

        if (pairingResults == null) {
            return;
        }

        for (Pairing pair : pairingResults) {
            TournamentPlayer white = players.get(pair.whitePlayerStartNo());
            white.getRounds().add(pair.whiteRound());

            if (pair.blackPlayerStartNo() > 0) {
                TournamentPlayer black = players.get(pair.blackPlayerStartNo());
                black.getRounds().add(pair.blackRound());
            }
        }

        //TournamentState.validateCurrentRound(tournamentState,tournamentState.getTournamentPlayers());
    }

    @Override
    public void setTournamentInfo(TournamentInfo info) {
        this.tournamentInfo = info;
    }

    public void setPairingListener(PairingListener l) {
        this.listener = l;
    }

}
