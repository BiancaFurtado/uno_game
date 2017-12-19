/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.player;

import java.util.ArrayList;
import model.card.Card;
import model.user.User;

/**
 *
 * @author sergi
 */
public class Player {
    private User user;
    /**
     * No inicio do jogo, para definir qual jogador iniciará a partida cada um recebe uma carta.
     * Ela será guardada em startCard
     */
    private Card startCard;
    private ArrayList<Card> cardsOnHand;
    private final PlayerType myType;
    public enum PlayerType{
        HUMAN,MACHINE
    }

    public Player(User user,PlayerType playerType) {
        this.user = user;
        this.myType =playerType;
        cardsOnHand = new ArrayList<>();
    }

    public PlayerType getMyType() {
        return myType;
    }

    
    
    public User getUser() {
        return user;
    }
    
    public ArrayList<Card> getCardsOnHand() {
        return cardsOnHand;
    }

    public void addCardsOnHand(Card card){
        cardsOnHand.add(card);
    }
    
    public void removeCardOnHand(int index){
        cardsOnHand.remove(index);
    }
    
    public Card getStartCard() {
        return startCard;
    }

    public void setStartCard(Card drawnCard) {
        this.startCard = drawnCard;
    }
 
    
    
}
