/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.card;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author sergi
 */
public class CardModel {

    public ArrayList<CardType> normalCardTypes = new ArrayList<>();
    public ArrayList<CardType> efectCardTypes = new ArrayList<>();
    public ArrayList<CardType> jokerCardTypes = new ArrayList<>();

    private ArrayList<Card> redCardList, greenCardList, blueCardList, yellowCardList, especialCardList;

    public CardModel() {
        //ArrayList Normal
        normalCardTypes.add(CardType.ZERO);
        normalCardTypes.add(CardType.ONE);
        normalCardTypes.add(CardType.TWO);
        normalCardTypes.add(CardType.THREE);
        normalCardTypes.add(CardType.FOUR);
        normalCardTypes.add(CardType.FIVE);
        normalCardTypes.add(CardType.SIX);
        normalCardTypes.add(CardType.SEVEN);
        normalCardTypes.add(CardType.EIGHT);
        normalCardTypes.add(CardType.NINE);

        //Especial Cartas
        efectCardTypes.add(CardType.CANCEL);
        efectCardTypes.add(CardType.PLUS_TWO);
        efectCardTypes.add(CardType.REVERSES);
        //Joker cards
        jokerCardTypes.add(CardType.JOKER);
        jokerCardTypes.add(CardType.PLUS_FOUR);
    }

    public Stack generateCardStack() {
        Stack<Card> newStack = new Stack<>();
        //Gerar cartas normais
        generateNormalCards();

        //Gerar cartas especiais
        generateEspecialCards();

        newStack.addAll(redCardList);
        newStack.addAll(greenCardList);
        newStack.addAll(blueCardList);
        newStack.addAll(yellowCardList);
        newStack.addAll(especialCardList);

        System.out.println("model.card.CardModel.generateCardStack()\n" + newStack + "\n Stack Size(" + newStack.size() + ")");

        return newStack;
    }

    private void generateNormalCards() {
        blueCardList = new ArrayList<>();
        greenCardList = new ArrayList<>();
        yellowCardList = new ArrayList<>();
        redCardList = new ArrayList<>();
        //Criar cartas normais
        for (int i = 0; i < normalCardTypes.size(); i++) {
            if (normalCardTypes.get(i) == CardType.ZERO) {
                Card cRed = new Card(CardType.ZERO, CardColor.RED, "images/cartas/vermelha/0.png");
                Card cBlue = new Card(CardType.ZERO, CardColor.BLUE, "images/cartas/azul/0.png");
                Card cYellow = new Card(CardType.ZERO, CardColor.YELLOW, "images/cartas/amarela/0.png");
                Card cGreen = new Card(CardType.ZERO, CardColor.GREEN, "images/cartas/verde/0.png");
                redCardList.add(cRed);
                blueCardList.add(cBlue);
                yellowCardList.add(cYellow);
                greenCardList.add(cGreen);
            } else {
                //Pra cada cor
                Card cRed = new Card(normalCardTypes.get(i), CardColor.RED, "images/cartas/vermelha/" + normalCardTypes.get(i).getValue() + ".png");
                Card cBlue = new Card(normalCardTypes.get(i), CardColor.BLUE, "images/cartas/azul/" + normalCardTypes.get(i).getValue() + ".png");
                Card cYellow = new Card(normalCardTypes.get(i), CardColor.YELLOW, "images/cartas/amarela/" + normalCardTypes.get(i).getValue() + ".png");
                Card cGreen = new Card(normalCardTypes.get(i), CardColor.GREEN, "images/cartas/verde/" + normalCardTypes.get(i).getValue() + ".png");

                redCardList.add(cRed);
                blueCardList.add(cBlue);
                yellowCardList.add(cYellow);
                greenCardList.add(cGreen);

                redCardList.add(cRed);
                blueCardList.add(cBlue);
                yellowCardList.add(cYellow);
                greenCardList.add(cGreen);
            }
        }

    }

    private void generateEspecialCards() {
        especialCardList = new ArrayList<>();
        //Para as cores
        for (int i = 0; i < efectCardTypes.size(); i++) {
            Card cRed = new Card(efectCardTypes.get(i), CardColor.RED, "images/cartas/vermelha/" + efectCardTypes.get(i).toString() + ".png");
            Card cBlue = new Card(efectCardTypes.get(i), CardColor.BLUE, "images/cartas/azul/" + efectCardTypes.get(i).toString() + ".png");
            Card cYellow = new Card(efectCardTypes.get(i), CardColor.YELLOW, "images/cartas/amarela/" + efectCardTypes.get(i).toString() + ".png");
            Card cGreen = new Card(efectCardTypes.get(i), CardColor.GREEN, "images/cartas/verde/" + efectCardTypes.get(i).toString() + ".png");

            redCardList.add(cRed);
            blueCardList.add(cBlue);
            yellowCardList.add(cYellow);
            greenCardList.add(cGreen);

            redCardList.add(cRed);
            blueCardList.add(cBlue);
            yellowCardList.add(cYellow);
            greenCardList.add(cGreen);
        }

        //Para +4 e Coringa
        for (int i = 0; i < jokerCardTypes.size(); i++) {
            Card cPlusFour = new Card(CardType.PLUS_FOUR, CardColor.NEUTRON, "images/cartas/plus_4.png");
            Card cJoker = new Card(CardType.JOKER, CardColor.NEUTRON, "images/cartas/joker.png");

            especialCardList.add(cPlusFour);
            especialCardList.add(cJoker);

            especialCardList.add(cPlusFour);
            especialCardList.add(cJoker);

        }
    }

}
