/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.about;

import view.MainFrameController;
import view.ViewController;
import view.menu.MenuPanelController;

/**
 *
 * @author sergi
 */
public class AboutPanelController implements ViewController{
    private AboutPanel myView;

    @Override
    public void startView() {
        myView = new AboutPanel(this);
        MainFrameController.setView(myView);
    }

    @Override
    public void returnPage() {
        new MenuPanelController().startView();
    }
}
