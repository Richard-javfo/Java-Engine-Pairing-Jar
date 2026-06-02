/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package trf.api;

import java.io.OutputStream;
import trf.impl.JavaTournamentPlayer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DP
 */
public interface PairingProvider {
      
    
    List<Pairing> execPairing( String options);
    
    void fill(List<Pairing> pairingResults,Map<Integer,TournamentPlayer> players); 
          
    // Ermöglicht es, den Listener global für diese Instanz zu setzen
    void setPairingListener(PairingListener listener);

   void setTournamentInfo(TournamentInfo info);
   
   void generateInitialTrf(OutputStream out);
}
