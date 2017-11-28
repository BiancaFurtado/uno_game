/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.user;

import data.dao.UserDAO;
import exception.DaoException;
import exception.ModelException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.game.GameModel;
import util.AppLog;

/**
 * Model de Usuario
 * @author sergi
 */
public class UserModel {
    public static User USER_LOGGED;
    private UserDAO myDAO;
    public UserModel(){
        this.myDAO = new UserDAO();
    }
    /**
     * Listar todos os usuarios do sistema
     * @return 
     */
    public List<User> getAllUsers(){
        return myDAO.getAll();
    }
    /**
     * Inserir usuarios
     * @param newUser 
     */
    public void insertUser(User newUser) throws ModelException{
        try {
            validateUserData(newUser);
            myDAO.INSERT(newUser);
        } catch (DaoException ex) {
            
        }
    }
    
    /**
     * Realizar login de usuario
     * @param userReceiver
     * @return 
     */
    public void doLogin(User userReceiver) throws ModelException {
        
        User searchUser;
        try {
            searchUser = myDAO.doLogin(userReceiver);
            USER_LOGGED = searchUser;
        } catch (DaoException ex) {
            throw new ModelException(ex.getMessage());
        }
    }

    private void validateUserData(User newUser) throws ModelException{
        if(newUser.getLogin().length()<4){
            throw new ModelException("O login deve possuir no mínimo 4 caracteres.");
        }
        if(newUser.getPassword().length()<8){
            throw new ModelException("A senha deve possuir no mínimo 8 caracteres.");
        }
    }

    public void updateUserLoggedData() throws ModelException{
        try {
            myDAO.UPDATE(USER_LOGGED);
        } catch (DaoException ex) {
            AppLog.error("Erro ao atualizar dados do usuário  "+" E: ("+ex.getMessage()+")");
        }
    }
    
    public void updateUser(User userToUpdate) throws ModelException{
        try {
            myDAO.UPDATE(userToUpdate);
        } catch (DaoException ex) {
            AppLog.error("Erro ao atualizar dados do usuário  "+" E: ("+ex.getMessage()+")");
        }
    }

   
}
