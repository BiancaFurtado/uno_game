/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.card;

/**
 *
 * @author sergi
 */
public class Card {
    private CardType cardType;
    private CardColor cardColor;
    private String iconSRC;
    private boolean efectActived;
    public Card(CardType myType, CardColor myColor,String iconSRC) {
        this.cardType = myType;
        this.cardColor = myColor;
        this.iconSRC = iconSRC;
    }

    public boolean isEfectActived() {
        return efectActived;
    }

    public void setEfectActived(boolean efectActived) {
        this.efectActived = efectActived;
    }
    
    
    
    
    public String getIconSRC() {
        return iconSRC;
    }
    
    public CardType getCardType() {
        return cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    @Override
    public String toString() {
        return "("+cardType.toString() +"-"+cardColor.toString()+")";
    }

    
    
    
    
}
