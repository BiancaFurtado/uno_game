/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import model.user.User;
import data.DataBase;
import exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.AppLog;
/**
 *
 * @author sergi
 */

/*
User Table
                + "	login VARCHAR(10),\n"
                + "	date_birth DATE NOT NULL,\n"
                + "	name VARCHAR(45) NOT NULL,\n"
                + "	src_profile TEXT NOT NULL,\n"
                + "	password TEXT NOT NULL,\n"
                + "	first_contact INT, \n"
*/
public class UserDAO implements DaoInterface{
    private Connection conn;
    /**
     * User DAO, classe responsável por manipular o User no banco de dados
     */
    public UserDAO() {
        this.conn = DataBase.getConnection();
    }
    
    @Override
    public void INSERT(Object myDAO) throws DaoException{
        User myUser = (User) myDAO;
        String sql = "INSERT INTO User (login,date_birth,name,src_profile,password) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            
            preparedStatement.setString(1,myUser.getLogin());
            preparedStatement.setDate(2,(java.sql.Date) myUser.getDateBirth());
            preparedStatement.setString(3,myUser.getName());
            preparedStatement.setString(4,myUser.getSrcProfile());
            preparedStatement.setString(5,myUser.getPassword());
            
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException ex) {
     
            switch(ex.getErrorCode()){
            case 19:
                AppLog.error("Usuário já existe "+" E: ("+ex.getMessage()+")");
                return;
            }
            AppLog.error("Erro desconhecido ao inserir em [User]"+" E: ("+ex.getMessage()+")");
            return;
        }
    }

    @Override
    public void UPDATE(Object myDAO)throws DaoException {
        User myUser = (User) myDAO;
        String sql = "UPDATE User SET login = ?,date_birth = ? ,name = ? ,src_profile = ? ,password = ?, first_contact = ? WHERE login = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            
            preparedStatement.setString(1,myUser.getLogin());
            preparedStatement.setDate(2,(java.sql.Date) myUser.getDateBirth());
            preparedStatement.setString(3,myUser.getName());
            preparedStatement.setString(4,myUser.getSrcProfile());
            preparedStatement.setString(5,myUser.getPassword());
            
            preparedStatement.setInt(6,myUser.getFirstContact());
            
            preparedStatement.setString(7,myUser.getLogin());
            
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (SQLException ex) {
            AppLog.error("Erro desconhecido ao atualizar em [User]"+" E: ("+ex.getMessage()+")");
            throw new DaoException("Erro ao atualizar Usuário");
        }
    }

    @Override
    public void DELETE(Object myDAO)throws DaoException {
    
    }
    /**
     * Listar todos os usuário do sistema
     * @return 
     */
    public List<User> getAll(){
        List<User> outList = new ArrayList<>();
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT login,date_birth,name,src_profile,first_contact FROM User");
            while(rs.next()){
                User actualUser = new User();
                actualUser.setLogin(rs.getString("login"));
                actualUser.setName(rs.getString("name"));
                actualUser.setDateBirth(rs.getDate("date_birth"));
                actualUser.setSrcProfile(rs.getString("src_profile"));
                actualUser.setFirstContact(rs.getInt("first_contact"));
                outList.add(actualUser);
            }
        } catch (SQLException ex) {
            AppLog.error("Erro desconhecido ao buscar todos os usuários em [User]"+" E: ("+ex.getMessage()+")");
        }
        return outList;
    }
    /**
     * Verificar login de usuário na base de dados
     * @param userReceive Ususario que deseja verificar login
     * @return 
     */
    public User doLogin(User userReceive) throws DaoException{
        String sql= "SELECT * FROM User WHERE login = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            
            preparedStatement.setString(1, userReceive.getLogin());
            preparedStatement.setString(2, userReceive.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                userReceive.setName(rs.getString("name"));
                userReceive.setFirstContact(rs.getInt("first_contact"));
                userReceive.setDateBirth(rs.getDate("date_birth"));
                userReceive.setSrcProfile(rs.getString("src_profile"));
                preparedStatement.close();
                conn.close();
                return userReceive;
                
            }
            preparedStatement.close();
            conn.close();
            throw new DaoException("Usuário ou senha incorreto.");
            
        } catch (SQLException ex) {
            AppLog.error("Erro ao realizar login de usuário "+userReceive.getLogin()+" E:"+ex.getMessage());
        }
        return null;
    }

   
    
}
