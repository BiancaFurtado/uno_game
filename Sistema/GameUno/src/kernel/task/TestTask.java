/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kernel.task;

import exception.ModelException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import kernel.AppTaskManager;
import model.user.User;
import model.user.UserModel;

/**
 * Task para desenvolvimento
 * @author sergi
 */
public class TestTask implements AppTask{

    @Override
    public void executeTasks() {
        AppTaskManager.TASK_FINALIZE  = false;
        //Quantidade de tarefas
        AppTaskManager.TASK_MAX_VALUE = 4;
 
        //TASK 1
        AppTaskManager.TASK_STATUS = "Inserindo usuário [teste] no banco...";
        //Corpo da task
        User userTest = new User();
        userTest.setLogin("teste");
        userTest.setPassword("12345678");
        userTest.setName("Usuário teste");
        userTest.setSrcProfile("user_1.png");
        userTest.setDateBirth(new Date(2132132));
        UserModel model = new UserModel();
        try {
            model.insertUser(userTest);
        } catch (ModelException ex) {
            System.out.println(ex.getMessage());
        }
        
        AppTaskManager.TASK_VALUE = 1;
        waitMillis(1000);
        
        //TASK 2
        AppTaskManager.TASK_STATUS = "Hihi teste developer!...";
        //Corpo da task
        AppTaskManager.TASK_VALUE = 2;
        waitMillis(1000);
        
        //TASK 3
        AppTaskManager.TASK_STATUS = "Calma ta aquase...";
        //Corpo da task
        AppTaskManager.TASK_VALUE = 3;
        waitMillis(1000);
        
        //TASK 4
        AppTaskManager.TASK_STATUS = "Vai lá...";
        //Corpo da task
        AppTaskManager.TASK_VALUE = 4;
        waitMillis(1000);
        
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
