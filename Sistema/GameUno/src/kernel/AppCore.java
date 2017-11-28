/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kernel;

import kernel.task.StartTask;
import view.MainFrameController;

/**
 *
 * @author sergi
 */
public class AppCore {
    /**
     * Classe responsável por iniciar a aplicação
     * @param args 
     */
    public static void main(String[] args){
        //Jogo 
        new Thread(() -> {
            new MainFrameController().startView();
        }).start();
        
    }
}
