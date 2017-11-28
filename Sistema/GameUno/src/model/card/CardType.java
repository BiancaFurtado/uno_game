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
public enum CardType {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    
    CANCEL(20),
    REVERSES(20),
    JOKER(20),
    PLUS_TWO(20),
    PLUS_FOUR(50);
    
    int value;
    CardType(int value) {    
        this.value= value;
    }
    public int getValue(){
        return value;
    }
}
