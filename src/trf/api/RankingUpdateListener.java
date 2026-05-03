/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package trf.api;

/**
 *
 * @author DP
 */
import java.util.List;

public interface RankingUpdateListener {

    // Diese Methode wird gerufen, wenn das Ranking fertig ist
    void onRankingUpdated(List<RankedPlayer> results);
}
