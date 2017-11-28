
import view.MainFrameController;
import view.menu.MenuPanelController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sergi
 */
public class main {
    public static void main(String[] args){
        MainFrameController.startNoContentFrame();
        MenuPanelController menuPanelControlle = new MenuPanelController();
        menuPanelControlle.onBtnStartClicked();
    }
}
