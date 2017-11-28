/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.game;

import view.game.GamePanelEventsInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;
import model.card.Card;
import model.card.CardColor;
import model.card.CardModel;
import model.card.CardType;
import model.player.Player;
import model.user.User;
import model.user.UserModel;

/**
 * Modelo de jogo
 *
 * @author LelaScarlet
 */
public class GameModel implements GamePanelEventsInterface {

    private Game actualGame;
    private GameEventsInterface gameEvents;

    private static GameModel myInstaModel;

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

        //Instanciar uma nova pilha de cartas que ja foram jogadas
        actualGame.setStackCardPlayed(new Stack());

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
        actualGame.setFirstPlayer(generateFirstPlayer());

        actualGame.setActualPlayer(actualGame.getFirstPlayer());

        //Distribui as cartas
        shareUserCards(players);

        //Jogo criado
        actualGame.setGameStatus(GameStatus.CREATED);
    }

    public Stack<Card> actualStakCard() {
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
    public Card getHeadStackPlayed() {
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
                    return 0;//Jogador humano (sempre vai estar na posição 0;
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
        //Primeiro player sempre é o usuario loggado
        Player p1 = new Player(UserModel.USER_LOGGED, Player.PlayerType.HUMAN);

        //Gerar Players Sem IA
        //TODO: Futuramente com IA
        User u2 = new User();
        u2.setName("Maquina 1");
        Player p2 = new Player(u2, Player.PlayerType.MACHINE);

        User u3 = new User();
        u3.setName("Maquina 2");
        Player p3 = new Player(u3, Player.PlayerType.MACHINE);

        User u4 = new User();
        u4.setName("Maquina 3");
        Player p4 = new Player(u4, Player.PlayerType.MACHINE);

        return new Player[]{p1, p2, p3, p4};

    }

    /**
     * *
     * Distribuir as cartas entre os players
     *
     * @param players
     */
    private void shareUserCards(Player[] players) {
        for (int i = 0; i < players.length; i++) {
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
        return this.actualGame.getFirstPlayer();
    }

    /**
     * *
     *
     * @return O jogador da vez
     */
    public Player getActualPlayer() {
        return actualGame.getPlayers()[actualGame.getActualPlayerIndex()];
    }

    @Override
    public void onGameViewLoaded() {
        gameEvents.shareStartCards();
    }

    @Override
    public void onStartCardsShared() {
        actualGame.setGameStatus(GameStatus.CREATED);
        gameEvents.distributeCards();
    }

    @Override
    public void onCardsShared() {
        actualGame.setGameStatus(GameStatus.RUNNING);

        if (getActualPlayer().getMyType() == Player.PlayerType.HUMAN) {
            gameEvents.requestLoggedPlayerCulp();
        } else {
            gameEvents.requestMachinePlayerCulp(actualGame.getActualPlayerIndex());
        }

    }

    @Override
    public void userLoggedCulp(Card cardToPlay) {
        //JOGADA DE USUARIO
        
    }

    @Override
    public void machineCulp() {
        //JOGADA DE IA
        System.out.println("MachineCulp");

        //DECIDIR QUAL MELHOR CARTA E ETC
        Card bestCardIndex = getActualPlayer().getCardsOnHand().get(0);
        //TODO: Verificar se há algum efeito de jogo para ser aplicado

        //Verificar se a pilha esta vazia para poder jogar
        if (actualGame.getStackCardPlayed().isEmpty()) {
            System.out.println("Pilha de jogadas esta vazia");
            executeCulp(bestCardIndex);
        } else {
            Card headStackPlayed = getHeadStackPlayed();
            //Verificar punicao
            if (headStackPlayed.isEfectActived()) {
                System.out.println("Carta de punição na mesa: "+headStackPlayed.getCardType().toString());
                //EXECUTA A PUNICAO E TIRA O EFEITO
                switch (headStackPlayed.getCardType()) {
                    case CANCEL:

                        break;
                    case PLUS_TWO:

                        break;
                    case PLUS_FOUR:

                        break;
                }
                headStackPlayed.setEfectActived(false);
                //ATUALIZAR TELA
                return;
            } else {
                executeCulp(bestCardIndex);
            }
        }

    }

    private void executeCulp(Card cardToPlay) {
        //Testar se é possivel jogar
        if (!actualGame.getStackCardPlayed().isEmpty()) {
            Card headStackPlayed = getHeadStackPlayed();

            //A carta da mao é normal e da mesa tbm
            if (isNormalCard(cardToPlay.getCardType()) && isNormalCard(headStackPlayed.getCardType())) {
                //Se elas possuem o mesmo numero ou a mesma cor
                if (((cardToPlay.getCardType().getValue() == headStackPlayed.getCardType().getValue()))
                        || cardToPlay.getCardColor().equals(headStackPlayed.getCardColor())) {
                    //JOGADA
                    
                } else {
                    //JOGADA RECUSADA
                }
                // A carta da mao é normal e a da mesa é um joker ou um +4
            } else if (isNormalCard(cardToPlay.getCardType()) && ((headStackPlayed.getCardType().equals(CardType.JOKER)) || (headStackPlayed.getCardType().equals(CardType.PLUS_FOUR)))) {//CARTA JOGADA FOR NORMAL E A  DA MESA FOR UM JOKER
                //Se for a mesma cor definida no jogo
                if (cardToPlay.getCardColor().equals(actualGame.getGameActualColor())) {
                    //JOGADA
                } else {
                    //JOGADA RECUSADA
                }
                //A carta da mao é normal e a da mesa é uma carta de efeito 
            } else if (isNormalCard(cardToPlay.getCardType()) && (isEfectCard(headStackPlayed.getCardType()))) {
                //Se elas possuem a mesma cor
                if (cardToPlay.getCardColor().equals(headStackPlayed.getCardColor())) {
                    //JOGADA
                } else {
                    //JOGADA RECUSADA
                }
                //A carta da mao é especial e a da mesa é normal
            } else if (isEspecialCard(cardToPlay.getCardType()) && isNormalCard(headStackPlayed.getCardType())) {
                if (isEfectCard(cardToPlay.getCardType())) {
                    //Se for de efeito, testa apenas a cor 
                    if (cardToPlay.getCardColor().equals(headStackPlayed.getCardColor())) {
                        //JOGADA
                    } else {
                        //JOGADA RECUSDA 
                    }
                } else {
                    //è um JOKER ou +4
                    
                    //JOGADA
                }
                //A carta da mao é de efeito e da mesa tbm
            }else{
                //JOGADA RECUSADA
            }
            //Nao existe carta na mesa
        } else {
            //Pode Jogar quaquer carta 
           
        }

    }

    private boolean isNormalCard(CardType cardType) {
        for (CardType attcard : CardModel.normalCardTypes) {
            if (attcard.equals(cardType)) {
                return true;
            }
        }
        return false;
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

    private boolean isEfectCard(CardType cardType) {
        for (CardType attcard : CardModel.especialCardTypes) {
            if (attcard.equals(cardType)) {
                return true;
            }
        }
        return false;
    }


}
