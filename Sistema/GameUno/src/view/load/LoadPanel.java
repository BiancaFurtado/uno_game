/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.load;


import java.util.logging.Level;
import java.util.logging.Logger;
import kernel.AppKernel;
import view.login.LoginController;


/**
 *
 * @author sergi
 */
public class LoadPanel extends javax.swing.JPanel {

    /**
     * Creates new form LoadPanel
     */
    public LoadPanel() {
        initComponents();
        progressBar.setMaximum(AppKernel.TASK_MAX_VALUE);
    }
    
    public void onLoadComplete(){
        this.setVisible(false);
        LoginController loginController = new LoginController();
        loginController.startView();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressBar = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        progressBar.setMaximum(10);
        add(progressBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 630, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/256x230_logo_uno.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, -1, -1));

        txtStatus.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        txtStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtStatus.setText("Progress");
        add(txtStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, 630, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel txtStatus;
    // End of variables declaration//GEN-END:variables

    public boolean loadApp() {
        while(true){
                     if(AppKernel.TASK_VALUE != AppKernel.TASK_MAX_VALUE){
                         txtStatus.setText(AppKernel.TASK_STATUS);
                         progressBar.setValue(AppKernel.TASK_VALUE);   
                     }else{
                         
                         return true;
                     }
        }
    }
}
