package Logic.Pages;

import ParameterClasses.Notification;
import SQLManaging.DBManager;
import java.util.List;


/**
 * Class that handles the logic of NotificationsUI
 */
public class NotificationsLogic {

    /**
     * @return an ArrayList of all relevant notifications for the current user
     */
    public static List<Notification> fetchNotifications(){
        List<Notification> notifications = DBManager.notificationTable.fetchRows(null);
        return notifications;
    }


}
