/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menu;


import kernel.task.GameTask;
import model.game.GameMode;
import model.user.UserModel;
import util.AppUtil;
import view.notification.NotificationTime;
import view.MainFrameController;
import view.ViewController;
import view.about.AboutPanelController;
import view.game.GamePanelController;
import view.load.LoadInterface;
import view.load.LoadPanel;
import view.login.LoginPanelController;
import view.notification.NotificationType;
import view.tutorial.TutorialPanelController;


/**
 *
 * @author sergi
 */
public class MenuPanelController implements ViewController{
    private MenuPanel myView;
    
    @Override
    public void startView() {
        myView = new MenuPanel(this);
        MainFrameController.setView(myView);
        //Pegar dados do ususario loggado
        String login = UserModel.USER_LOGGED.getLogin();
        String name = UserModel.USER_LOGGED.getName();
        String srcUser = UserModel.USER_LOGGED.getSrcProfile();
        
        myView.setUserInfos(login, name, srcUser);
        //Caso o usuario logado esteja usando o sistema pela primeira vez
        if(UserModel.USER_LOGGED.getFirstContact()==AppUtil.USER_FIRST_CONTACT_VALUE){
            new TutorialPanelController().startView();
        }
    }
  
    
    
    /**
     * Logout do jogo
     */
    public void logout() {
        if(myView.showConfirmDialog("Deseja realizar logout?","Logout")){
        } else {
            new LoginPanelController().startView();
            UserModel.USER_LOGGED = null;
        }
    }    
    public void showAbout(){
        new AboutPanelController().startView();
    }

    @Override
    public void returnPage() {
        
    }

    public void showTutorial() {
        new TutorialPanelController().startView();
    }

    void onBtnContinueClicked() {
        MainFrameController.shootNotification(NotificationType.INFO, "Você ainda não possui jogo salvo, tente iniciar um novo.",NotificationTime.MEDIUM);
    }

    void onBtnRankingClicked() {
        MainFrameController.shootNotification(NotificationType.WARNING, "O ranking está indisponível.",NotificationTime.SHORT);
    }

    public void onBtnStartClicked() {
        GameMode gameMode = GameMode.SINGLE;
        new Thread(() -> {
            new GameTask(gameMode).executeTasks();
        }).start();
        MainFrameController.setView(new LoadPanel(new LoadInterface() {
            @Override
            public void onLoadComplete() {
                new GamePanelController().startView();
            }
        }));
    }
}
