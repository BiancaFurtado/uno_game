/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.game;

import view.MainFrameController;
import view.ViewController;

/**
 *
 * @author sergi
 */
public class GamePanelController implements ViewController{
    private GamePanel myView;
    @Override
    public void startView() {
        myView = new GamePanel(this);
        MainFrameController.setView(myView);
    }

    @Override
    public void returnPage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}