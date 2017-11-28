/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JPanel;
import kernel.task.StartTask;
import view.notification.NotificationTime;
import view.load.LoadInterface;
import view.load.LoadPanel;
import view.login.LoginPanelController;
import view.notification.NotificationType;

/**
 * Frame principal
 * @author sergi
 */
public class MainFrameController {
    private static MainFrame mainFrame;
    
    public static MainFrame getMainFrame(){
        return mainFrame;
    }
    public static void shootNotification(NotificationType notificationType,String message,NotificationTime time) {
        mainFrame.shootNotification(notificationType,message,time);
    }

    public static void enableMainFame(boolean b) {
        mainFrame.setEnabled(b);
    }

    public static void exitApp() {
        System.out.println("Saiu");
        System.exit(0);
    }

 
    public MainFrameController(){
        mainFrame = new MainFrame();
    }
    /**
     * Metodo para startar a view incial da aplicação
     */
    public static void startView(){
        mainFrame.setVisible(true);
        //Start Task
        new Thread(() -> {
            new StartTask().executeTasks();
        }).start();
        // Set view com a Load para observar o carregamento
        setView(new LoadPanel(new LoadInterface() {
            @Override
            public void onLoadComplete() {
                new Thread(() -> {
                    //Linha para testes
                        //new TestTask().executeTasks();
                }).start();
                MainFrameController.setView(new LoadPanel(new LoadInterface() {
                    @Override
                    public void onLoadComplete() {
                        LoginPanelController loginController = new LoginPanelController();
                        loginController.startView();
                    }
                }));
            }
        }));
    }
    
    /**
     * Metodo para setar um Panel novo na Frame principal
     * @param view 
     */
    public static void setView(JPanel view){
        System.out.println("view.MainFrameController.setView()"+view.getClass().getName());
        view.setVisible(true);
        mainFrame.setContentPane(view);
        mainFrame.revalidate();
        System.out.println("Visible for user: "+mainFrame.getContentPane().isShowing());
    }

    public static void startNoContentFrame(){
         if(mainFrame==null){
             mainFrame = new MainFrame();
         }
         mainFrame.setVisible(true);
    }

}
