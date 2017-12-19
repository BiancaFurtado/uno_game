/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.game;

import exception.GameException;
import view.game.GamePanelEventsInterface;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;
import model.card.Card;
import model.card.CardColor;
import model.card.CardModel;
import model.card.CardType;
import model.game.Game.Sense;
import model.player.Player;
import model.user.User;
import model.user.UserModel;
import util.AppLog;

/**
 * Modelo de jogo
 *
 * @author LelaScarlet
 */
public class GameModel implements GamePanelEventsInterface {

    private Game actualGame;
    private GameEventsInterface gameEvents;

    private static GameModel myInstaModel;
    private int indexCardAux;

    private GameModel() {
    }

    /**
     * Iniciar um novo jogo
     *
     * @param gameMode
     */
    public void startNewGame(GameMode gameMode) {

        actualGame = new Game();

        //Setar o modo de jogo
        actualGame.setGameMode(gameMode);

        //Definir o sentido inicial do jogo
        actualGame.setGameSense(Sense.RIGTH);

        //Instanciar uma nova pilha de cartas que ja foram jogadas
        actualGame.setStackCardPlayed(new Stack());

        actualGame.setStackCard(new Stack());
        //Gerar a pilha de cartas para o jogo
        CardModel cardModel = new CardModel();
        Stack newStack = cardModel.generateCardStack();

        //Embaralhar cartas
        shuffleCards(newStack);

        //Atribuir a pilha de cartas gerada ao jogo
        actualGame.setStackCard(newStack);

        //Gerar os players da partida
        Player[] players = generatePlayers();

        actualGame.setPlayers(players);

        //Decidir e Atribuir o primeiro jogador
        actualGame.setFirstPlayerPosition(generateFirstPlayer());

        //Atribuir o jogador da vez
        actualGame.setActualPlayerPosition(actualGame.getFirstPlayerPosition());

        //Distribui as cartas
        shareCards(players);

        //Jogo criado
        updateGameStatus(GameStatus.CREATED);

    }

    public Stack<Card> getActualStakCard() {
        return actualGame.getStackCard();
    }

    public void setGameStatusInterface(GameEventsInterface gameStatusInterface) {
        this.gameEvents = gameStatusInterface;
    }

    /**
     * Retirar a primeira carta do topo da pilha
     *
     * @return
     */
    private Card popStackGame() {
        return actualGame.getStackCard().pop();
    }

    /**
     * Metodo para buscar a primeira carta da pilha de cartas joigadas
     *
     * @return
     */
    private Card getHeadStackPlayed() {
        try {
            return actualGame.getStackCardPlayed().peek();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Selecionar o jogador que iniciará o jogo
     *
     * @return
     */
    private int generateFirstPlayer() {
        Stack<Card> allCards = (Stack<Card>) actualGame.getStackCard().clone();
        Random random = new Random();
        int index = 0;
        for (Player player : actualGame.getPlayers()) {
            index = random.nextInt(allCards.size());
            while (isEspecialCard(allCards.get(index).getCardType())) {
                index = random.nextInt(allCards.size());
            }
            player.setStartCard(allCards.get(index));
            allCards.remove(index);
        }
        int firstPlayer = checkCards(actualGame.getPlayers());
        return firstPlayer;
    }

    private int checkCards(Player[] players) {
        int cardBigger = -1;
        int firstPlayer = 0;//Posição do jogador a iniciar a partida
        for (int i = 0; i < players.length; i++) {
            if (players[i].getStartCard().getCardType().getValue() > cardBigger) {
                cardBigger = players[i].getStartCard().getCardType().getValue();
                firstPlayer = i;
            }
        }
        for (int i = 0; i < players.length; i++) {
            for (int j = i + 1; j < players.length; j++) {
                //Se doi
                if (players[i].getStartCard().getCardType().getValue()
                        == players[j].getStartCard().getCardType().getValue()) {
                    return 0;//Jogador humano (sempre vai estar na posição 0);
                }
            }
        }

        return firstPlayer;
    }

    /**
     * Gerar os players para nova partida
     *
     * @return
     */
    private Player[] generatePlayers() {
        ArrayList<String> srcUsed = new ArrayList<>();
        //Primeiro player sempre é o usuario loggado
        Player p1 = new Player(UserModel.USER_LOGGED, Player.PlayerType.HUMAN);
        srcUsed.add(p1.getUser().getSrcProfile());
        //Gerar Players Sem IA
        //TODO: Futuramente com IA
        User u2 = new User();
        u2.setName("Maquina 1");
        u2.setSrcProfile(generateOtherIconSrc(srcUsed));
        Player p2 = new Player(u2, Player.PlayerType.MACHINE);

        User u3 = new User();
        u3.setName("Maquina 2");
        u3.setSrcProfile(generateOtherIconSrc(srcUsed));
        Player p3 = new Player(u3, Player.PlayerType.MACHINE);

        User u4 = new User();
        u4.setName("Maquina 3");
        u4.setSrcProfile(generateOtherIconSrc(srcUsed));
        Player p4 = new Player(u4, Player.PlayerType.MACHINE);

        return new Player[]{p1, p2, p3, p4};

    }

    /**
     * *
     * Distribuir as cartas entre os players
     *
     * @param players
     */
    private void shareCards(Player[] players) {
        for (int i = 0; i < players.length; i++) {;
            for (int j = 0; j < 7; j++) {
                players[i].addCardsOnHand(popStackGame());
            }
        }
    }

    /**
     * *
     * Single
     *
     * @return
     */
    public static GameModel myInstance() {
        if (myInstaModel == null) {
            myInstaModel = new GameModel();
        }
        return myInstaModel;
    }

    /**
     * Carregar jogo do usuario, caso exista
     *
     * @param user
     * @return
     */
    public boolean loadGameSavedFromUser(User user) {
        return false;
    }

    /**
     * Meotodo para embaralhar as cartas
     *
     * @param stackCard
     */
    private void shuffleCards(Stack<Card> stackCard) {
        ArrayList<Card> listCards = new ArrayList<>();
        while (!stackCard.isEmpty()) {
            listCards.add(stackCard.pop());
        }
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int n1 = random.nextInt(listCards.size());

            Card auxCard = listCards.get(n1);

            int n2 = random.nextInt(listCards.size());
            Card auxCard2 = listCards.get(n2);

            listCards.set(n1, auxCard2);
            listCards.set(n2, auxCard);
        }
        Iterator<Card> iterable = listCards.iterator();
        while (iterable.hasNext()) {
            stackCard.push(iterable.next());
        }
        System.out.println("Pilha embaralhada: " + stackCard);
    }

    /**
     * Metodo para buscar todos os jogadores
     *
     * @return
     */
    public Player[] getGamePlayers() {
        return actualGame.getPlayers();
    }

    /**
     * *
     *
     * @return A posicao do jogador definido para iniciar a partida
     */
    public int getFirstPlayer() {
        return this.actualGame.getFirstPlayerPosition();
    }

    /**
     * *
     *
     * @return O jogador da vez
     */
    public Player getActualPlayer() {
        return actualGame.getPlayers()[actualGame.getActualPlayerPosition()];
    }

    @Override
    public void onGameViewLoaded() {
        gameEvents.shareStartCards();
        gameEvents.updateGameStatus(actualGame.getGameStatus());
    }

    @Override
    public void onStartCardsShared() {
        updateGameStatus(GameStatus.STARTED);
        gameEvents.distributeCards();
    }

    @Override
    public void onCardsShared() {
        updateGameStatus(GameStatus.RUNNING);

        if (getActualPlayer().getMyType() == Player.PlayerType.HUMAN) {
            gameEvents.requestLoggedPlayerCulp();
        } else {
            gameEvents.requestMachinePlayerCulp(actualGame.getActualPlayerPosition());
        }

    }

    @Override
    public void machineCulp() {
        try {
            //JOGADA DE IA
            int cardIndex = resolveMachineCard();
            executeCulp(cardIndex);
            gameEvents.culpExecuted();
        } catch (GameException ex) {
            System.out.println(ex.getMessage());
            changePlayer();
            gameEvents.culpExecuted();
        }

    }

    @Override
    public void userCulp(int cardIndex) {
        try {
            //JOGADA DE USUARIO
            executeCulp(cardIndex);
            gameEvents.disableUserLoggedComponents();
            gameEvents.culpExecuted();
        } catch (GameException ex) {
            gameEvents.showCulpRefused();
        }

    }

    private void verifyPunition() throws GameException {
        //Verificar punicao
        if (actualGame.isTableEfected()) {
            System.out.println("Carta de punição na mesa: " + getHeadStackPlayed().getCardType().toString());
            //EXECUTA A PUNICAO E RETIRA O EFEITO DA MESA
            switch (getHeadStackPlayed().getCardType()) {
                case CANCEL:
                    gameEvents.refreshPlayerCards(getActualPlayerPosition());

                    actualGame.setTableEfected(false);
                    System.out.println("Player :" + getActualPlayer().getUser().getName() + " recebeu uma punição de " + getHeadStackPlayed().getCardType().toString());
                    throw new GameException(GamePunition.CANCEl);
                case PLUS_TWO:
                    plusCardToActualPlayer(2);
                    gameEvents.refreshPlayerCards(getActualPlayerPosition());

                    actualGame.setTableEfected(false);
                    System.out.println("Player :" + getActualPlayer().getUser().getName() + " recebeu uma punição de " + getHeadStackPlayed().getCardType().toString());
                    throw new GameException(GamePunition.PLUS_TWO);

                case PLUS_FOUR:
                    plusCardToActualPlayer(4);
                    gameEvents.refreshPlayerCards(getActualPlayerPosition());

                    actualGame.setTableEfected(false);
                    System.out.println("Player :" + getActualPlayer().getUser().getName() + " recebeu uma punição de " + getHeadStackPlayed().getCardType().toString());
                    throw new GameException(GamePunition.PLUS_FOUR);
            }
            System.out.println("Fim do fluxo de punição");

        }

    }

    private void executeCulp(int cardIndex) throws GameException {
        System.out.println("Jogada de :" + getActualPlayer().getUser().getName());
        Card cardToPlay = getActualPlayer().getCardsOnHand().get(cardIndex);
        Card tableCard = getHeadStackPlayed();
        boolean doCulp = false;
        //Permissoes de jogadas
        if (tableCard != null) {
            System.out.println("Cor atual do jogo: " + actualGame.getGameActualColor().toString());
            if (((isNormalCard(cardToPlay.getCardType()) && isNormalCard(tableCard.getCardType()))
                    || ((isNormalCard(cardToPlay.getCardType())) && (isEfectCard(tableCard.getCardType()))))
                    || ((isNormalCard(tableCard.getCardType())) && (isEfectCard(cardToPlay.getCardType())))
                    || ((isNormalCard(cardToPlay.getCardType())) && (isJokerCard(tableCard.getCardType())))) {
                //Testa a cor ou  numero
                if ((cardToPlay.getCardColor().equals(actualGame.getGameActualColor()))
                        || (cardToPlay.getCardType().getValue() == tableCard.getCardType().getValue())) {
                    //Faça a jogada
                    doCulp = true;
                }
            } else if ((isEfectCard(cardToPlay.getCardType()) && isEfectCard(tableCard.getCardType()))) {
                if ((cardToPlay.getCardColor().equals(actualGame.getGameActualColor()))
                        || (cardToPlay.getCardType().equals(tableCard.getCardType()))) {
                    if ((!cardToPlay.getCardType().equals(CardType.PLUS_TWO) && !tableCard.getCardType().equals(CardType.PLUS_TWO) || !actualGame.isTableEfected())) {
                        //Faça a jogada
                        doCulp = true;
                    }
                }
            } else if ((isEfectCard(cardToPlay.getCardType()) && isJokerCard(tableCard.getCardType()))) {
                if ((cardToPlay.getCardColor().equals(actualGame.getGameActualColor()))) {
                    //Faça a jogada
                    doCulp = true;
                }
            } else if ((isEfectCard(tableCard.getCardType()) && isJokerCard(cardToPlay.getCardType()))
                    || (isNormalCard(tableCard.getCardType()) && isJokerCard(cardToPlay.getCardType()))) {
                doCulp = true;
            }
        } else {
            doCulp = true;
        }
        if (doCulp) {
            if (isPunitionCard(cardToPlay.getCardType())) {
                actualGame.setTableEfected(true);
            }
            if (cardToPlay.getCardType().equals(CardType.REVERSES)) {
                gameEvents.showAnimationToPunition(null, GamePunition.REVERSES);
                if (actualGame.getGameSense().equals(Sense.RIGTH)) {
                    actualGame.setGameSense(Sense.LEFT);
                } else {
                    actualGame.setGameSense(Sense.RIGTH);
                }
            }
            getActualStakCardPlayed().push(cardToPlay);
            if (isNormalCard(cardToPlay.getCardType()) || isEfectCard(cardToPlay.getCardType())) {
                switchGameColor(cardToPlay.getCardColor());
            } else {
                switchGameColor(CardColor.BLUE);
            }
            doCulp(cardIndex);

        } else {
            throw new GameException("Jogada recusada");
        }
    }

    private boolean isNormalCard(CardType cardType) {
        switch (cardType) {
            case ZERO:
                return true;
            case ONE:
                return true;
            case TWO:
                return true;
            case THREE:
                return true;
            case FOUR:
                return true;
            case FIVE:
                return true;
            case SIX:
                return true;
            case SEVEN:
                return true;
            case EIGHT:
                return true;
            case NINE:
                return true;
            default:
                return false;
        }
    }

    private boolean isEspecialCard(CardType cardType) {
        switch (cardType) {
            case CANCEL:
                return true;
            case REVERSES:
                return true;
            case JOKER:
                return true;
            case PLUS_TWO:
                return true;
            case PLUS_FOUR:
                return true;
            default:
                return false;
        }
    }

    private boolean isJokerCard(CardType cardType) {
        switch (cardType) {
            case PLUS_FOUR:
                return true;
            case JOKER:
                return true;
            default:
                return false;
        }
    }

    private boolean isEfectCard(CardType cardType) {
        switch (cardType) {
            case PLUS_TWO:
                return true;
            case CANCEL:
                return true;
            case REVERSES:
                return true;
            default:
                return false;
        }
    }

    private boolean isPunitionCard(CardType cardType) {
        switch (cardType) {
            case CANCEL:
                return true;
            case PLUS_TWO:
                return true;
            case PLUS_FOUR:
                return true;
            default:
                return false;
        }
    }

    /**
     * Metodo para mudar para o proximo jogador
     */
    private void changePlayer() {
        int side = 1;
        if (actualGame.getGameSense().equals(Sense.LEFT)) {
            side = -1;
        }
        int actualPosition = actualGame.getActualPlayerPosition();
        boolean positionNotSeted = true;
        while (positionNotSeted) {
            actualPosition += side;

            if (actualPosition >= 4) {
                actualPosition = 0;
            } else if (actualPosition < 0) {
                actualPosition = 3;
            }

            actualGame.setActualPlayerPosition(actualPosition);
            positionNotSeted = false;

        }

    }

    public Card[] getCountCardsStackPlayed(int i) {
        Stack<Card> t = (Stack<Card>) actualGame.getStackCardPlayed().clone();
        Card[] cards = new Card[i];
        for (int j = 0; j < i; j++) {
            try {
                cards[j] = t.pop();
            } catch (Exception e) {
                System.out.println("(Jogada) Pilha de cartas jogadas nao possui cartas suficientes para retornar às :" + i + " cartas iniciais");
            }
        }
        return cards;
    }

    public Card[] getCountCardsStack(int i) {
        Stack<Card> t = (Stack<Card>) actualGame.getStackCard().clone();
        Card[] cards = new Card[i];
        for (int j = 0; j < i; j++) {
            try {
                cards[j] = t.pop();
            } catch (Exception e) {
                System.out.println("(Não Jogada) Pilha de cartas nao possui cartas suficientes para retornar às :" + i + " cartas iniciais");
            }
        }
        return cards;
    }

    public int getActualPlayerPosition() {
        return actualGame.getActualPlayerPosition();
    }

    private void plusCardToActualPlayer(int quant) {
        try {
            for (int i = 0; i < quant; i++) {
                getActualPlayer().getCardsOnHand().add(getActualStakCard().pop());
            }
        } catch (EmptyStackException e) {
            System.out.println("Pilha de cartas está vazia");
            AppLog.error("Pilha de cartas está vazia");
        }
    }

    public Stack<Card> getActualStakCardPlayed() {
        return actualGame.getStackCardPlayed();
    }

    @Override
    public void onViewUpdate() {
        try {
            verifyPunition();
            if (getActualPlayer().getMyType() == Player.PlayerType.HUMAN) {
                gameEvents.requestLoggedPlayerCulp();
            } else {
                gameEvents.requestMachinePlayerCulp(actualGame.getActualPlayerPosition());
            }
        } catch (GameException ex) {
            gameEvents.showAnimationToPunition(getActualPlayer(), ex.getPunitionType());
            changePlayer();
            gameEvents.culpExecuted();
        }
    }

    private String generateOtherIconSrc(ArrayList<String> srcUsed) {
        String[] srcAvaliable = new String[]{
            "user_1.png",
            "user_2.png",
            "user_3.png",
            "user_4.png",
            "user_5.png"
        };
        for (String actulSrc : srcAvaliable) {
            if (!srcUsed.contains(actulSrc)) {
                srcUsed.add(actulSrc);
                return actulSrc;
            }
        }
        return null;
    }

    private void updateGameStatus(GameStatus gameStatus) {
        actualGame.setGameStatus(gameStatus);
        try {
            gameEvents.updateGameStatus(gameStatus);
        } catch (Exception e) {
            System.out.println("Interface GameEvents esta null no momento");
        }
    }

    public void setGameFinalTime(int[] current_time) {
        actualGame.setGameTime(current_time);
    }

    public int[] getGameCurrentTime() {
        return actualGame.getGameTime();
    }
    public Game getActualGame(){
        return actualGame;
    }
    
    /***
     * Metodo para dropar 1 carta para o usuario i
     * @param i Posicao do jogador na mesa
     */
    public void popStackCardForUser(int i) {
        try {
            getGamePlayers()[i].getCardsOnHand().add(getActualStakCard().pop());
            gameEvents.refreshPlayerCards(getActualPlayerPosition());
        } catch (NullPointerException e) {
            System.out.println("Interface Null");
        }
    }

    public void updateGameSide(Sense sense) {
        actualGame.setGameSense(sense);
    }

    private void switchGameColor(CardColor cardColor) {
        actualGame.setGameActualColor(cardColor);
        gameEvents.switchActualGameColor(cardColor);
    }

    @Override
    public void skipCulp() {
        plusCardToActualPlayer(1);
        gameEvents.refreshPlayerCards(getActualPlayerPosition());
        if (getActualPlayerPosition() == 0) {
            gameEvents.disableUserLoggedComponents();
        }
        gameEvents.showAnimationToPunition(getActualPlayer(), GamePunition.PLUS_ONE);
        changePlayer();
        gameEvents.culpExecuted();
    }

    @Override
    public void onUserSelectedNewColor(CardColor c) {
        switchGameColor(c);
        doCulp(indexCardAux);
        indexCardAux = -1;
    }

    private void doCulp(int cardIndex) {
        getActualPlayer().getCardsOnHand().remove(cardIndex);
        //Aqui verifica se tem apenas uma carta na mão e ativa o botao uno

        if (getActualPlayer().getCardsOnHand().isEmpty()) {
            //Jogador nao possui cartas na mao
            //Contar pontuação de cada jogador restante
            int sum = 0;
            for (int i = 0; i < actualGame.getPlayers().length; i++) {
                if (i != getActualPlayerPosition()) {
                    for (Card card : actualGame.getPlayers()[i].getCardsOnHand()) {
                        sum += card.getCardType().getValue();
                    }
                }
            }
            updateGameStatus(GameStatus.FINALIZED);
            gameEvents.refreshPlayerCards(getActualPlayerPosition());
            gameEvents.finalizeGame(sum);
        } else {
            gameEvents.refreshPlayerCards(getActualPlayerPosition());
            changePlayer();
        }
    }

    private int resolveMachineCard() {
        Card tableCard = getHeadStackPlayed();
        for (int i = 0; i < getActualPlayer().getCardsOnHand().size(); i++) {
            Card actual = getActualPlayer().getCardsOnHand().get(i);
            if (culpIsPossible(actual, tableCard)) {
                return i;
            }
        }
        return 0;
    }

    private boolean culpIsPossible(Card cardToPlay, Card tableCard) {
        boolean doCulp = false;
        //Permissoes de jogadas
        if (tableCard != null) {
            if (((isNormalCard(cardToPlay.getCardType()) && isNormalCard(tableCard.getCardType()))
                    || ((isNormalCard(cardToPlay.getCardType())) && (isEfectCard(tableCard.getCardType()))))
                    || ((isNormalCard(tableCard.getCardType())) && (isEfectCard(cardToPlay.getCardType())))
                    || ((isNormalCard(cardToPlay.getCardType())) && (isJokerCard(tableCard.getCardType())))) {
                //Testa a cor ou  numero
                if ((cardToPlay.getCardColor().equals(actualGame.getGameActualColor()))
                        || (cardToPlay.getCardType().getValue() == tableCard.getCardType().getValue())) {
                    //Faça a jogada
                    doCulp = true;
                }
            } else if ((isEfectCard(cardToPlay.getCardType()) && isEfectCard(tableCard.getCardType()))) {
                if ((cardToPlay.getCardColor().equals(actualGame.getGameActualColor()))
                        || (cardToPlay.getCardType().equals(tableCard.getCardType()))) {
                    if ((!cardToPlay.getCardType().equals(CardType.PLUS_TWO) && !tableCard.getCardType().equals(CardType.PLUS_TWO) || !actualGame.isTableEfected())) {
                        //Faça a jogada
                        doCulp = true;
                    }
                }
            } else if ((isEfectCard(cardToPlay.getCardType()) && isJokerCard(tableCard.getCardType()))) {
                if ((cardToPlay.getCardColor().equals(actualGame.getGameActualColor()))) {
                    //Faça a jogada
                    doCulp = true;
                }
            } else if ((isEfectCard(tableCard.getCardType()) && isJokerCard(cardToPlay.getCardType()))
                    || (isNormalCard(tableCard.getCardType()) && isJokerCard(cardToPlay.getCardType()))) {
                doCulp = true;
            }
        } else {
            doCulp = true;
        }
        return doCulp;
    }

}
