/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.notification;

import util.NotificationTime;
import view.notification.NotificationType;

/**
 *
 * @author sergi
 */
public interface NotificationManagerInterface {
    public void notify(NotificationType notificationType,String message,NotificationTime time);
}