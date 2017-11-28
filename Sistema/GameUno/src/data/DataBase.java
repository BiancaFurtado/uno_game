/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.AppLog;

/**
 *
 * @author sergi
 */
public class DataBase {
    /**
     * Metodo parar criar a database local
     * @return true caso seja criado com sucesso, false caso contrario
     */
    public boolean createDataBase() 
    {
        Connection conn = getConnection();
        PreparedStatement prepSteaStatement;
        //Criar a tabela User
        String sql_user = "CREATE TABLE IF NOT EXISTS User (\n"
                + "	login VARCHAR(10),\n"
                + "	date_birth DATE NOT NULL,\n"
                + "	name VARCHAR(45) NOT NULL,\n"
                + "	src_profile TEXT NOT NULL,\n"
                + "	password TEXT NOT NULL,\n"
                + "	first_contact INT DEFAULT 1, \n"
                + "	CONSTRAINT my_primary PRIMARY KEY (login)\n"
                + ");";
        try {
            prepSteaStatement = conn.prepareStatement(sql_user);
            prepSteaStatement.execute();
        } catch (SQLException ex) {
            AppLog.error("Fail on create table [User] (E: "+ex.getMessage()+")");
            System.err.println("Fail on create table [User] (E: "+ex.getMessage()+")");
            return false;
        }
        //Criar a tabela Game
        String sql_game = "CREATE TABLE IF NOT EXISTS Game (\n"
                + "	id INT AUTO_INCREMENT,\n"
                + "	date_start DATE,\n"
                + "	user_login VARCHAR(10),\n"
                + "	CONSTRAINT my_primary PRIMARY KEY (id),\n"
                + "	CONSTRAINT game_user FOREIGN KEY (user_login) REFERENCES User(login) ON UPDATE CASCADE ON DELETE RESTRICT\n"
                +");";
        try {
            prepSteaStatement = conn.prepareStatement(sql_game);
            prepSteaStatement.execute();
        } catch (SQLException ex) {
             AppLog.error("Fail on create table [Game] (E: "+ex.getMessage()+")");
            System.err.println("Fail on create table [Game] (E: "+ex.getMessage()+")");
            return false;
        }
        //Criar a tabela de Ranking
        String sql_rank = "CREATE TABLE IF NOT EXISTS Ranking (\n"
                + "	id INT AUTO_INCREMENT,\n"
                + "	point FLOAT,\n"
                + "	time TIME(10),\n"
                + "	user_login VARCHAR(10),\n"
                + "	CONSTRAINT my_primary PRIMARY KEY (id),\n"
                + "	CONSTRAINT ranking_user FOREIGN KEY (user_login) REFERENCES User(login) ON UPDATE CASCADE ON DELETE RESTRICT\n"
                +");";
        try {
            prepSteaStatement = conn.prepareStatement(sql_rank);
            prepSteaStatement.execute();
        } catch (SQLException ex) {
            AppLog.error("Fail on create table [Ranking] (E: "+ex.getMessage()+")");
            System.err.println("Fail on create table [Ranking] (E: "+ex.getMessage()+")");
            return false;
        }
        return true;
    }
    /**
     * Metodo para gerar uma conexão com o banco local
     * @return uma Connection SQLite
     */
    public static Connection getConnection()
    {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:uno.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
        } catch (SQLException e) {
            AppLog.error("Fail to connect database (E: "+e.getMessage()+")");
            System.err.println("Fail to connect database (E: "+e.getMessage()+")");
        }
        return conn;
    }
    
    
}
