/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.api;

/**
 *
 * @author DP
 */
public abstract class AbstractPairingProvider implements PairingProvider {
    
    protected PairingListener listener;
    @Override
    public void setPairingListener(PairingListener listener) {
        this.listener = listener;
    }

    // Hilfsmethode für alle Erben, um sicher Nachrichten zu senden
    protected void notifyStatus(String msg) {
        if (listener != null) listener.onStatusUpdate(msg);
    }

    protected void notifyError(String err, String dump) {
        if (listener != null) listener.onError(err, dump);
    }
}
