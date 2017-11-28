/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.game;

/**
 *
 * @author Bianca
 */
public interface GameEventsInterface {
    public void shareStartCards();
    
    public void distributeCards();
    
    public void requestLoggedPlayerCulp();

    public void culpExecuted();

    public void requestMachinePlayerCulp(int playerIndex);
    
   
    
}
