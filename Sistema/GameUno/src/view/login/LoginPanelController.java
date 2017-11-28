/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.login;

import kernel.task.UserTask;
import exception.ModelException;
import model.user.User;
import model.user.UserModel;
import view.MainFrameController;
import view.ViewController;
import view.load.LoadInterface;
import view.load.LoadPanel;
import view.menu.MenuPanelController;
import view.register.RegisterPanelController;


/**
 * Controller da view de Login
 * @author sergi
 */
public class LoginPanelController implements ViewController{
    private LoginPanel myView;
    public LoginPanelController(){
        myView  = new LoginPanel(this);
        
        //Linha para testes
        onBtnLoginClicked("sergioivad", "123456789");
    }
    /**
     * Responder ao botao de login
     * @param login
     * @param senha 
     */
    public void onBtnLoginClicked(String login, String senha){
        //Realizar login
        UserModel model = new UserModel();
        User userTryLogin = new User();
        userTryLogin.setLogin(login);
        userTryLogin.setPassword(senha);
        try {
            model.doLogin(userTryLogin);
            myView.showAlertDialog("Login realizado com sucesso.");
            loginSuccess();
        } catch (ModelException ex) {
                myView.showAlertDialog(ex.getMessage());
        }
    }
    
    /**
     * Realizar login
     */
    public void loginSuccess(){
        //Carregar jogo, iniciar task de jogo

        //Definir Task para executar
        new Thread(() -> {
            new UserTask().executeTasks();
        }).start();
        
        MainFrameController.setView(new LoadPanel(new LoadInterface() {
            @Override
            public void onLoadComplete() {
                MenuPanelController menuPanelController = new MenuPanelController();
                menuPanelController.startView();
            }
        }));
    }
    @Override
    public void startView() {
        MainFrameController.setView(myView);
    }

    void onBtnRegisterClicked() {
        RegisterPanelController registerController = new RegisterPanelController();
        registerController.startView();
    }

    @Override
    public void returnPage() {
        
    }

    void onBtnOutClicked() {
        if(myView.showConfirmDialog("Deseja sair do jogo?", "Sair")){    
            MainFrameController.exitApp();
        }
    }

    

}
