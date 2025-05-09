package UI.Panels;

import javax.swing.*;

import ParameterClasses.Post;
<<<<<<< HEAD
=======
import UI.Pages.ShowPost;
import UI.BaseFrame;

>>>>>>> aac6dfdd0eace6a1dda0f6fcf3fc3487e3d937e5
import java.awt.*;
import java.util.List;


/**
 * This class allows a full display of a certain users post or post display of all users
 */
public class GridPanel extends JPanel {
    private List<Post> posts;
    private JPanel contentPanel;
    private boolean hasPosts;

    /**
     * the grid panel is created with popular posts fetched throw a database view
     * @param posts a list containing all posts to be displayed
     */
    public GridPanel(List<Post> posts) {
        setLayout(new BorderLayout());

        // Use FlowLayout with LEFT alignment to ensure items start from top-left
        contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Remove padding

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Set viewport view to start from the very top
        scrollPane.getVerticalScrollBar().setValue(0);

        add(scrollPane, BorderLayout.CENTER);

        this.posts = posts;

        loadPosts();
    }

    // Load media files (Images & Videos)

    /**
     * Post loading of corresponding user
     */
    private void loadPosts() {
        if (posts == null || posts.isEmpty()) {
            showNoPostsMessage();
            return;
        }

        hasPosts = false;

        // Calculate panel width to determine how many images fit per row
        int containerWidth = getWidth();
        if (containerWidth <= 0) containerWidth = 300; // Default if not yet sized

        int imageWidth = 80; // Width of each image container
        int imagesPerRow = Math.max(1, containerWidth / imageWidth);

        // Create a wrapper panel with grid layout to ensure proper row organization
        JPanel gridWrapper = new JPanel(new GridLayout(0, imagesPerRow, 5, 5));
        gridWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        gridWrapper.setAlignmentY(Component.TOP_ALIGNMENT);

        for (Post post : posts) {
            if (post != null) {
                // Create a fixed-size container for each post
                JPanel postContainer = new JPanel();
                postContainer.setPreferredSize(new Dimension(90, 90));
                postContainer.setLayout(new BorderLayout());

                JPanel postPanel = new RegularDisplayedPost(post, 80, 80);

                postContainer.add(postPanel, BorderLayout.CENTER);
                gridWrapper.add(postContainer);
                hasPosts = true;
            }
        }


        // Add the grid wrapper to the content panel
        contentPanel.removeAll();
        contentPanel.add(gridWrapper, BorderLayout.NORTH);

        if (!hasPosts) {
            showNoPostsMessage();
        }

        revalidate();
        repaint();
    }


    @Override
    public void addNotify() {
        super.addNotify();
        // Force a reload when the component is added to ensure proper sizing
        SwingUtilities.invokeLater(this::reloadGrid);
    }

    /**
     * reloads the page once posts are loaded
     */
    private void reloadGrid() {
        if (hasPosts) {
            // This forces the grid to recalculate with the actual container width
            loadPosts();
        }
    }

    /**
     * Message display in case of no posts to show
     */
    private void showNoPostsMessage() {
        contentPanel.removeAll();
        JLabel message = new JLabel("No posts available", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 16));
        contentPanel.add(message);
        revalidate();
        repaint();
    }
}