/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trf.tiebreak;

/**
 *
 * @author DP
 */
public abstract class DiscardTiebreak implements TiebreakStrategy{
    
    private final int discardLowest;
    private final int discardHighest;

    protected int getDiscardLowest() {
        return discardLowest;
    }

    protected int getDiscardHighest() {
        return discardHighest;
    }

    public DiscardTiebreak(int discardLowest, int discardHighest) {
        this.discardLowest = discardLowest;
        this.discardHighest = discardHighest;
    }

    public DiscardTiebreak() {
        this.discardLowest = 0;
        this.discardHighest = 0;
    }
    
    
    
}
