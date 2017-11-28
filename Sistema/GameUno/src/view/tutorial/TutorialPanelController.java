/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.tutorial;

import exception.ModelException;
import model.user.UserModel;
import util.AppUtil;
import view.MainFrameController;
import view.ViewController;
import view.menu.MenuPanelController;

/**
 *
 * @author sergi
 */
public class TutorialPanelController implements ViewController{
    private TutorialPanel myView;
    @Override
    public void startView() {
        myView = new TutorialPanel(this);
        MainFrameController.setView(myView);
        if(UserModel.USER_LOGGED.getFirstContact() == AppUtil.USER_FIRST_CONTACT_VALUE){
            UserModel.USER_LOGGED.setFirstContact(0);
            
            UserModel userModel = new UserModel();
            try {
                userModel.updateUserLoggedData();
            } catch (ModelException ex) {
                myView.showAlertDialog(ex.getMessage());
            }
        }
    }

    @Override
    public void returnPage() {
        
        new MenuPanelController().startView();
    }
    
}
