/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.user;

import java.sql.Date;

/**
 * Entidade de Usuario
 * @author sergi
 */
public class User {
    private String login;
    private Date dateBirth;
    private String name;
    private String srcProfile;
    private String password;
    private int firstContact;

    public String getLogin() {
        return login;
    }

    public void setFirstContact(int firstContact) {
        this.firstContact = firstContact;
    }

    
    public int getFirstContact() {
        return firstContact;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrcProfile() {
        return srcProfile;
    }

    public void setSrcProfile(String srcProfile) {
        this.srcProfile = srcProfile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
    
    
}
