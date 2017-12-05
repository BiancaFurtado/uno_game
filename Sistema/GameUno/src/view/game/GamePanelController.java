/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.game;

import java.awt.event.MouseAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import model.card.Card;
import model.card.CardType;
import model.game.GameModel;
import util.AppLog;
import view.MainFrameController;
import view.ViewController;
import model.game.GameEventsInterface;
import model.game.GameStatus;

import view.menu.MenuPanelController;
import view.notification.NotificationTime;
import view.notification.NotificationType;

/**
 *
 * @author sergi
 */
public class GamePanelController implements ViewController, GameEventsInterface {

    private GamePanel myView;
    private GameModel gameModel = GameModel.myInstance();
    private GameStatus actualGameStatus;
    private final int MAX_TIME_TO_CULP = 8;

    private boolean userClickedOnCard = false;
    private boolean userPopStackCard = false;

    public GamePanelController() {
        gameModel.setGameStatusInterface(this);

    }

    @Override
    public void startView() {
        myView = new GamePanel(this);
        MainFrameController.setView(myView);
        loadPlayersInfos();
    }

    @Override
    public void returnPage() {
        new MenuPanelController().startView();
    }

    @Override
    public void shareStartCards() {
////        System.out.println("view.game.GamePanelController.shareStartCards()");
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
        MainFrameController.shootNotification(NotificationType.SUCCESS, "Inicie o jogo!", NotificationTime.MEDIUM);
        refreshActiveActualUser();
        myView.showStartButton();
    }

    private void setUserLoggedCardsEnable(boolean b) {
        for (int j = 0; j < 7; j++) {
            myView.getLabels(0)[j].setEnabled(b);
        }
    }

    @Override
    public void refreshPlayerCards(int userIndex) {
        myView.setInvisibleUserCardsLabel(userIndex);//Desabilitar todas, antes de atualizar
        refreshPlayerCardsCount(userIndex);
        int cardsCount = gameModel.getGamePlayers()[userIndex].getCardsOnHand().size();
        for (int i = 0; i < cardsCount; i++) {
            if (userIndex == 0) {//Apenas para o usuario Logado
                if (i < 7) {
                    String srcImg = gameModel.getGamePlayers()[userIndex].getCardsOnHand().get(i).getIconSRC();
                    myView.getLabels(userIndex)[i].setIcon(new ImageIcon(getClass().getResource("/" + srcImg)));
                    //Atribuir o click para cada label
                    myView.getLabels(userIndex)[i].setName(String.valueOf(i));
                    myView.getLabels(userIndex)[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            myView.onUserLabelCardClicked(evt);
                        }
                    });
                } else {
                    //Limpar o ArrayList
                }
            }
            if (i < 7) {
                myView.getLabels(userIndex)[i].setVisible(true);
            }
        }
    }

    @Override
    public void distributeCards() {
        refreshStacksGame();
        distributePlayerCards();
        setUserLoggedCardsEnable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!Thread.currentThread().isInterrupted()) {
                    int[] current_time = gameModel.getGameCurrentTime();
                    if (current_time == null) {
                        current_time = new int[]{0, 0};
                    }
                    while (!actualGameStatus.equals(GameStatus.FINALIZED)) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GamePanelController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (current_time[1] < 60) {
                            current_time[1]++;
                        } else {
                            current_time[0]++;
                            current_time[1] = 0;
                        }
                        myView.updateLabel(current_time[0] + ":" + current_time[1]);
                    }
                    gameModel.setGameFinalTime(current_time);
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

    }

    void onBtnStartClicked() {
        gameModel.onStartCardsShared();
    }

    @Override
    public void requestLoggedPlayerCulp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!Thread.currentThread().isInterrupted()) {
                    int time_remaining = MAX_TIME_TO_CULP;
                    //System.out.println(".run() do RequestLoggedPlayerCulp");
                    setUserLoggedCardsEnable(true);
                    myView.setTimePlayerVisible(0, true);
                    myView.updateTimeForUser(0, MAX_TIME_TO_CULP);
                    while (time_remaining > 0 && !userClickedOnCard && !userPopStackCard) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GamePanelController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        time_remaining--;
                        myView.updateTimeForUser(0, time_remaining);
                    }
                    myView.setTimePlayerVisible(0, false);
                    setUserLoggedCardsEnable(false);
                    if (!userPopStackCard) {
                        gameModel.userCulp(myView.getSelectedUserCard());
                        userClickedOnCard = false;
                        myView.setUserCardSelectedIndex(0);
                    } else {
                        userPopStackCard = false;
                    }
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    @Override
    public void culpExecuted() {
        refreshStacksGame();
        refreshActiveActualUser();
        gameModel.onViewUpdate();
    }

    @Override
    public void requestMachinePlayerCulp(int machinePlayerIndex) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!Thread.currentThread().isInterrupted()) {
                    int time_remaining = MAX_TIME_TO_CULP;
                    //System.out.println(".run() do RequestMachinePlayerCulp");
                    myView.setTimePlayerVisible(machinePlayerIndex, true);
                    myView.updateTimeForUser(machinePlayerIndex, MAX_TIME_TO_CULP);
                    while (time_remaining > 0) {
                        if (time_remaining == 5) {//Com alguns segundos à IA joga
                            myView.setTimePlayerVisible(machinePlayerIndex, false);
                            gameModel.machineCulp();
                            //refreshPlayerCards(machinePlayerIndex);
                            Thread.currentThread().interrupt();

                            return;

                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GamePanelController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        time_remaining--;
                        myView.updateTimeForUser(machinePlayerIndex, time_remaining);
                    }
                }
            }
        }).start();

    }

    private void refreshStacksGame() {
        //Atualizar labels para as cartas jogadas
        Card[] cards_played = gameModel.getCountCardsStackPlayed(3);
        myView.setStackPlayedVisible(false);
        if (cards_played[0] != null) {
            myView.getLabels(11)[1].setVisible(true);
            myView.getLabels(11)[1].setText(String.valueOf(gameModel.getActualStakCardPlayed().size()));
        } else {
            myView.getLabels(11)[1].setVisible(false);
        }
        for (int i = 0; i < cards_played.length; i++) {
            try {
                String scrIconCard = cards_played[i].getIconSRC();
                myView.getLabels(6)[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/" + scrIconCard)));
                myView.getLabels(6)[i].setVisible(true);
            } catch (Exception e) {
                System.out.println("Falha ao atualizar labels de cartas jogadas");
            }
        }
        //Atualizar labels daas cartas que ainda nao foram jogadas
        Card[] cards_not_played = gameModel.getCountCardsStack(3);
        if (cards_not_played[0] != null) {
            myView.getLabels(11)[0].setVisible(true);
            myView.getLabels(11)[0].setText(String.valueOf(gameModel.getActualStakCard().size()));
        } else {
            myView.getLabels(11)[0].setVisible(false);
        }
        myView.setStartCardVisible(false);
        for (int i = 0; i < cards_not_played.length; i++) {
            try {
                myView.getLabels(7)[i].setVisible(true);
            } catch (Exception e) {
                System.out.println("Falha ao atualizar labels de cartas");
            }
        }
    }

    private void refreshActiveActualUser() {
        myView.setActiveUserLabelsVisible(false);
        myView.getLabels(5)[gameModel.getActualPlayerPosition()].setVisible(true);
    }

    void executeInstantUserCulp(boolean b) {
        this.userClickedOnCard = b;
    }

    private void distributePlayerCards() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!Thread.currentThread().isInterrupted()) {
                    for (int i = 0; i < gameModel.getGamePlayers().length; i++) {
                        for (int j = 0; j < 7; j++) {
                            try {
                                myView.getLabels(i)[j].setVisible(true);
                                refreshPlayerCardsCount(i);
                                Thread.sleep(150);
                            } catch (Exception e) {
                                System.out.println("Erro ao atribuir carta");
                            }

                        }

                    }
                    myView.setStartCardVisible(false);
                    refreshPlayerCards(0);
                    gameModel.onCardsShared();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    @Override
    public void showAnimationToPunition(CardType type) {
        System.out.println("Animacao para efeito");
    }

    @Override
    public void finalizeGame(int sum) {
        myView.showPointsWinner(sum, gameModel.getActualPlayer().getUser().getName());
        returnPage();
        gameModel.setGameStatusInterface(null);
    }

    private void loadPlayersInfos() {
        for (int i = 0; i < gameModel.getGamePlayers().length; i++) {
            String srcIcon = gameModel.getGamePlayers()[i].getUser().getSrcProfile();
            myView.getLabels(8)[i].setIcon(new ImageIcon(getClass().getResource("/images/user/" + srcIcon)));
            myView.getLabels(9)[i].setText(gameModel.getGamePlayers()[i].getUser().getName());
        }
        gameModel.onGameViewLoaded();
    }

    @Override
    public void updateGameStatus(GameStatus gameStatus) {
        this.actualGameStatus = gameStatus;
    }

    private void refreshPlayerCardsCount(int userIndex) {
        int cardsCount = gameModel.getGamePlayers()[userIndex].getCardsOnHand().size();
        if (cardsCount > 0) {
            myView.getLabels(10)[userIndex].setVisible(cardsCount > 0);
            myView.getLabels(10)[userIndex].setText(String.valueOf(cardsCount));
        } else {
            myView.getLabels(10)[userIndex].setVisible(false);
        }
    }

    void onClickedStackCards() {
        if (gameModel.getActualPlayerPosition() == 0) {
            userPopStackCard = true;
            gameModel.popStackCardForUser(0);
            refreshPlayerCardsCount(0);
        }
    }

    @Override
    public void refreshStacks() {
        refreshStacksGame();
    }

}
