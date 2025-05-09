package UI.Pages;

import Logic.Pages.ExploreUILogic;
import UI.Panels.GridPanel;
import UI.TemplateUI;

import javax.swing.*;

/**
 * Explore UI is the class that creates the explore page in the main app.
 * Users can look up other users or view posts of all users
 * <p>
 * The page consists of:
 * <li>A search bar at the top to find users</li>
 * <li>A grid panel that displays content (presumably posts from users)</li>
 * </p>
 */
public class ExploreUI extends TemplateUI {

    /**
     * Default constructor.
     * Initializes the UI by calling the superclass constructor.
     *
     * @see TemplateUI
     */
    public ExploreUI() {
        super();
    }

    /**
     * Initializes the UI components.
     * This method is called from the TemplateUI parent class during construction.
     * It adds the main content panel to the UI and ensures proper focus and rendering.
     */
    @Override
    protected void initializeUI() {

        add(createMainContentPanel());

        SwingUtilities.invokeLater(() -> requestFocusInWindow());
        // Add panels to the frame
        revalidate();
        repaint();
    }

    /**
     * Returns the name of this UI page for display in the header.
     *
     * @return the name of this page
     */
    @Override
    public String getName() {
        return "explore";
    }

    /**
     * Creates and returns the main content panel for the explore page.
     * This panel contains:
     * A grid panel that displays content
     *
     * @return JPanel containing the main content components
     * @see GridPanel
     * @see InstagramProfileUI
     */
    private JPanel createMainContentPanel() {
        // Post Grid
        GridPanel postGridPanel = new GridPanel(ExploreUILogic.fetchPosts()); // fix? maybe add view?


        // Main content panel that holds both the search bar and the image grid
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.add(postGridPanel);
        return mainContentPanel;
    }
}