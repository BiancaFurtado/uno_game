/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kernel.task;

import data.DataBase;
import java.util.logging.Level;
import java.util.logging.Logger;
import kernel.AppTaskManager;

/**
 * Task iniciar componentes cruciais para a aplicação
 * @author sergi
 */
public class StartTask implements AppTask {
     
    @Override
    public void executeTasks() {
        AppTaskManager.TASK_FINALIZE  = false;
        //Quantidade de tarefas
        AppTaskManager.TASK_MAX_VALUE = 2;
        
        //TASK 1 (Carregar e preparar DataBase)
        AppTaskManager.TASK_STATUS = "Preparando banco de dados local...";
        //Corpo da task
        DataBase dataBase = new DataBase();
        dataBase.createDataBase();
        AppTaskManager.TASK_VALUE = 1;
        waitMillis(1000);
        
        AppTaskManager.TASK_VALUE = 2;
        //Fim da task
        AppTaskManager.TASK_FINALIZE  = true;
        AppTaskManager.TASK_VALUE = 0;
    }

    @Override
    public void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
