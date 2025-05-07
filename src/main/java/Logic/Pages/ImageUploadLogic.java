package Logic.Pages;

import ParameterClasses.Post;
import SQLManaging.DBManager;

/**
 * Class that handles the logic of ImageUploadUI
 */
public class ImageUploadLogic{

    /**
     * Recieves a post pbject and saves it in the tables using the database
     */
    public static void saveImage(Post post) {
        // updates the POSTS table - adds the new post
        DBManager.postTable.insert(post);
    }

}
