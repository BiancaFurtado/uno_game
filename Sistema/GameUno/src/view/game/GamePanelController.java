/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.game;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.game.GameModel;
import util.AppLog;
import view.MainFrameController;
import view.ViewController;
import model.game.GameEventsInterface;
import view.menu.MenuPanel;
import view.menu.MenuPanelController;

/**
 *
 * @author sergi
 */
public class GamePanelController implements ViewController, GameEventsInterface {

    private GamePanel myView;
    private GameModel gameModel = GameModel.myInstance();
    private final int MAX_TIME = 5;

    public GamePanelController() {
        gameModel.setGameStatusInterface(this);
        
    }

    @Override
    public void startView() {
        myView = new GamePanel(this);
        MainFrameController.setView(myView);
        gameModel.onGameViewLoaded();
    }

    @Override
    public void returnPage() {
        new MenuPanelController().startView();
    }

    @Override
    public void shareStartCards() {
        System.out.println("view.game.GamePanelController.shareStartCards()");
        for (int i = 0; i < gameModel.getGamePlayers().length; i++) {
            String scrUserCard = gameModel.getGamePlayers()[i].getStartCard().getIconSRC();
            try {
                Thread.sleep(200);
                myView.getLabels(4)[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/" + scrUserCard)));
                myView.getLabels(4)[i].setVisible(true);

            } catch (Exception e) {
                System.out.println("Erro ao atribuir carta de inicio: " + scrUserCard + " E:" + e.getMessage());
                AppLog.error("Erro ao atribuir carta de inicio: " + scrUserCard + " E:" + e.getMessage());
            }
        }
        myView.getLabels(5)[gameModel.getFirstPlayer()].setVisible(true);
        myView.showStartButton();
    }

    private void refreshUsersCards(boolean distribute) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < gameModel.getGamePlayers().length; i++) {
                    for (int j = 0; j < gameModel.getGamePlayers()[i].getCardsOnHand().size(); j++) {
                        String scrUserCard = gameModel.getGamePlayers()[i].getCardsOnHand().get(j).getIconSRC();
                        try {
                            if (i == 0) {
                                myView.getLabels(i)[j].setIcon(new javax.swing.ImageIcon(getClass().getResource("/" + scrUserCard)));
                            }
                            myView.getLabels(i)[j].setVisible(true);
                            if (distribute) {
                                Thread.sleep(200);
                            }
                        } catch (Exception e) {
                            System.out.println("Erro ao atribuir carta :" + scrUserCard + " E:" + e.getMessage());
                        }

                    }

                }
                if (distribute) {
                    myView.setStartCardVisible(false);
                    gameModel.onCardsShared();
                }
            }
        }).start();

    }

    private void setUserLoggedCardsEnable(boolean b) {
        for (int j = 0; j < gameModel.getGamePlayers()[0].getCardsOnHand().size(); j++) {
            myView.getLabels(0)[j].setEnabled(b);
        }
    }

    @Override
    public void distributeCards() {
        refreshUsersCards(true);
        setUserLoggedCardsEnable(false);
    }

    void onBtnStartClicked() {
        gameModel.onStartCardsShared();
    }

    @Override
    public void requestLoggedPlayerCulp() {
       
         new Thread(new Runnable() {
            int time_remaining = MAX_TIME;
            @Override
            public void run() {
                setUserLoggedCardsEnable(true);
                myView.setTimePlayerVisible(0, true);
                myView.updateTimeForUser(0, MAX_TIME);
                while (time_remaining > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GamePanelController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    time_remaining--;
                    myView.updateTimeForUser(0, time_remaining);
                }
                myView.setTimePlayerVisible(0, false);
                myView.setTimePlayerVisible(0, false);
            }
        }).start();
    }

    @Override
    public void culpExecuted() {
        refreshUsersCards(false);
        refreshStacksGame();
    }

    private void refreshStacksGame() {

    }

    @Override
    public void requestMachinePlayerCulp(int playerIndex) {
        myView.setTimePlayerVisible(playerIndex, true);
        new Thread(new Runnable() {
            int time_remaining = MAX_TIME;

            @Override
            public void run() {
                myView.updateTimeForUser(playerIndex, MAX_TIME);
                while (time_remaining > 0) {
                    if (time_remaining == 3) {//Com 3 segundos a IA joga
                        myView.setTimePlayerVisible(playerIndex, false);
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GamePanelController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    time_remaining--;
                    myView.updateTimeForUser(playerIndex, time_remaining);
                }
                myView.setTimePlayerVisible(playerIndex, false);
            }
        }).start();
        gameModel.machineCulp();
    }

}
