/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.register;

import exception.ModelException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.user.User;
import model.user.UserModel;
import view.MainFrameController;
import view.ViewController;
import view.login.LoginPanelController;

/**
 *
 * @author sergi
 */
public class RegisterPanelController implements ViewController{
    private RegisterPanel myview;
    
    public RegisterPanelController(){
        this.myview = new RegisterPanel(this);
    }
    
    @Override
    public void startView() {
        MainFrameController.setView(myview);
    }

    void registerUser(String login, String name, String datebirth, String password,String srcProfile) {
        User user = new User();
        user.setLogin(login);
        user.setName(name);
        
        //Formatar data 
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.sql.Date data = new java.sql.Date(fmt.parse(datebirth).getTime());
            user.setDateBirth(data);
        } catch (ParseException ex) {
            myview.showAlertDialog("Por favor, informe uma data de nascimento válida.\n Formato esperado: dia/mês/ano");
        }
        //Restante 
        user.setPassword(password);
        user.setSrcProfile(srcProfile);
        //Inserir
        UserModel model = new UserModel();
        try {
            model.insertUser(user);
            myview.showAlertDialog("Cadastro realizado com sucesso.");
            returnPage();
        } catch (ModelException ex) {
            myview.showAlertDialog(ex.getMessage());
        }
        
                
    }

    @Override
    public void returnPage() {
        new LoginPanelController().startView();
    }
    
}
