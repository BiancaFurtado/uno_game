/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kernel.task;

import java.util.logging.Level;
import java.util.logging.Logger;
import kernel.AppTaskManager;
import model.game.GameMode;
import model.game.GameModel;
import model.user.UserModel;

/**
 * Task para caarregar informaçoes necessarias antes da tela de menu
 * @author sergi
 */
public class GameTask implements AppTask{
    private GameMode gameMode;
    public GameTask(GameMode gameMode){
        this.gameMode =gameMode;
    }
    
    @Override
    public void executeTasks() {
        AppTaskManager.TASK_FINALIZE  = false;
        //Quantidade de tarefas
        AppTaskManager.TASK_MAX_VALUE = 4;
        
        //TASK 1
        AppTaskManager.TASK_STATUS = "Verificando jogos do usuário...";
        //Corpo da task
        GameModel gameModel = GameModel.myInstance();
        AppTaskManager.TASK_VALUE = 1;
        waitMillis(500);
        //TASK 2
        AppTaskManager.TASK_STATUS = "Carregando dados...";
        //Corpo da task
         if(!gameModel.loadGameSavedFromUser(UserModel.USER_LOGGED)){
            //Se não, iniciar um novo jogo
            //TASK 3
            AppTaskManager.TASK_STATUS = "Iniciando novo jogo...";
            //Corpo da task
            gameModel.startNewGame(gameMode);
            AppTaskManager.TASK_VALUE = 2;
            waitMillis(1000);
        }
        
        AppTaskManager.TASK_VALUE = 3;
        waitMillis(3000);
       
        //TASK 4
        AppTaskManager.TASK_STATUS = "Pronto...";
        //Corpo da task     
        AppTaskManager.TASK_VALUE = 4;
        waitMillis(1000);
        
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
