/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kernel.task;

/**
 * Interface para qualquer Task do sistema
 * @author sergi
 */
public interface AppTask {
    /*
        
        //TASK N
        AppTaskManager.TASK_STATUS = "STATUS...";
        //Corpo da task
        AppTaskManager.TASK_VALUE = N;
        waitMillis(1000);
    
    */
    /**
     * Metodo para executar a task instanciada
     */
    public void executeTasks();
    /**
     * Metodo para a task aguardar por alguns segundos e continuar seu Job
     * @param millis 
     */
    public void waitMillis(long millis);
}
