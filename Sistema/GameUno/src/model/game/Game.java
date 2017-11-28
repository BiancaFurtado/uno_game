/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.game;

import java.util.Stack;
import model.card.Card;
import model.card.CardColor;
import model.player.Player;


/**
 *
 * @author sergi
 */
public class Game {
    private Stack<Card> stackCard;
    private Stack<Card> stackCardPlayed;
    
    private Player[] players = new Player[4];
    private GameMode gameMode;
    private GameStatus gameStatus;
    private CardColor gameActualColor;
    
    private int gameFirstPlayer;
    private int actualPlayerIndex;
    
    public GameMode getGameMode() {
        return gameMode;
    }

    public CardColor getGameActualColor() {
        return gameActualColor;
    }

    public void setGameActualColor(CardColor gameActualColor) {
        this.gameActualColor = gameActualColor;
    }

    public int getGameFirstPlayer() {
        return gameFirstPlayer;
    }

    public void setGameFirstPlayer(int gameFirstPlayer) {
        this.gameFirstPlayer = gameFirstPlayer;
    }

    
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Stack<Card> getStackCardPlayed() {
        return stackCardPlayed;
    }

    public void setStackCardPlayed(Stack stackCardPlayed) {
        this.stackCardPlayed = stackCardPlayed;
    }
    
    

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
    
    
    public Stack<Card> getStackCard() {
        return stackCard;
    }

    public void setStackCard(Stack stackCard) {
        this.stackCard = stackCard;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setFirstPlayer(int selectFirstPlayer) {
        this.gameFirstPlayer = selectFirstPlayer;
    }
    
    public int getFirstPlayer() {
         return this.gameFirstPlayer;
    }

    void setActualPlayer(int playerPosition) {
        this.actualPlayerIndex = playerPosition;
    }

    public int getActualPlayerIndex() {
        return actualPlayerIndex;
    }
    
    
    
}
