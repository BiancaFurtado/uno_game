/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JPanel;

/**
 * Interface para rtodas as VoiewControllers do sistema
 * @author sergi
 */
public interface ViewController {
    /**
     * Metodo para iniciar a view da controller 
     */
   public void startView();
   public void returnPage(); 
}
