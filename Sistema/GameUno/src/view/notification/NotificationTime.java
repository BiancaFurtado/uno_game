/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.notification;

/**
 *
 * @author sergi
 */
public enum NotificationTime {
    SHORT(3),LONG(8), MEDIUM(5);
    private final int value;
    /***
     * Tempo de exibição da notificação
     * @param time Tmepo em segundos
     */
    NotificationTime(int time){
       this.value=time;
    }
    public int getValue(){
        return value;
    }
}
