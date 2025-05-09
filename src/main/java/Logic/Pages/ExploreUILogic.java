package Logic.Pages;

import ParameterClasses.Post;
import SQLManaging.DBManager;

import java.util.List;

/**
 * Class that handles the logic of ExploreUI
 */
public class ExploreUILogic {
    /**
     * fetches the explore page posts for display based on views from the database
     * @return a list of popular posts
     */
    public static List<Post> fetchPosts() {
        return DBManager.exploreTable.fetchRows(null);
    }
}
