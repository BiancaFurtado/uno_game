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
import model.user.User;
import model.user.UserModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sergi
 */
public class GameModelTest {
    
    public GameModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        User a = new User();
        a.setLogin("Teste");
        a.setName("Teste teste");
        UserModel.USER_LOGGED =a ;
        GameModel.myInstance().startNewGame(GameMode.SINGLE);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of myInstance method, of class GameModel.
     */
    @Test
    public void testMyInstance() {
        System.out.println("myInstance");
        GameModel expResult = GameModel.myInstance();
        GameModel result = GameModel.myInstance();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of setGameFinalTime method, of class GameModel.
     */
    @Test
    public void testSetGameFinalTime() {
        System.out.println("setGameFinalTime");
        int[] current_time = new int[]{10,5};
        GameModel instance = GameModel.myInstance();
        instance.setGameFinalTime(current_time);
        
        int[] teste = new int[]{10,5};
        
        assertArrayEquals(current_time, teste);
        
    }
    /**
     * Test of updateGameSide method, of class GameModel.
     */
    @Test
    public void testUpdateGameSide() {
        System.out.println("updateGameSide");
        GameModel instance = GameModel.myInstance();
        
        Game.Sense sense = Game.Sense.RIGTH;
        Game.Sense gameSense = instance.getActualGame().getGameSense();
        assertEquals(sense,gameSense);
        
        sense = Game.Sense.LEFT;
        instance.updateGameSide(sense);
        
        assertNotEquals(sense,gameSense);
        
        }

    
}
