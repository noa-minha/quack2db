package UI.Pages;

import ParameterClasses.Notification;
import SQLManaging.DBManager;
import UI.TemplateUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * notificationsUI is the class that creates the notification page in the main app.
 * Users can view notifications they received from other users interaction with them, such as new follower or like on post
 */
public class NotificationsUI extends TemplateUI {
    private JPanel contentPanel;
    private List<Notification> notifications;
    private JScrollPane scrollPane;



    /**
     * Default constructor.
     * Initializes the UI by calling the superclass constructor.
     * @throws Exception 
     * @see TemplateUI
     */
    public NotificationsUI() {
        super();
    }


    /**
     * Initializes the UI components.
     * This method is called from the TemplateUI parent class during construction.
     * It adds the main content panel to the UI and ensures proper focus and rendering.
     */
    @Override
    public void initializeUI() {
        // Content Panel for notifications
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        

        //get all notifications for this user
        notifications = DBManager.notificationTable.fetchRows(null);

        if (notifications.size() > 0){
            for (Notification notification : notifications){
                String notificationMessage = notification.printNotification();
                JLabel notificationLabel = new JLabel(notificationMessage);
                contentPanel.add(notificationLabel);
            }
        }
        else{
            JLabel notificationLabel = new JLabel("No notifications", SwingConstants.CENTER);
            notificationLabel.setFont(new Font("Arial", Font.BOLD, 16));
            contentPanel.add(notificationLabel);
        }
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }
        

    /**
     * Returns the name of this UI page for display in the header.
     * @return the name of this page
     */
    @Override
    public String getName() {
        return "Notifications";
    }


}
