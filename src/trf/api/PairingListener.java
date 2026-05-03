/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.api;

import java.util.List;

/**
 *
 * @author DP
 */
public interface PairingListener {

    void onStatusUpdate(String message);

    void onError(String error, String technicalDump);

    void onPairingCompleted(int count);

    
}
