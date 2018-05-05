package cz.muni.pb138project;

/**
 * Native XML database API manager.
 *
 */
public interface XMLDatabaseManager {

    /**
     * Creates a new media category and adds it to the database.
     * @param category name of the category to be created
     * @return
     */
    String createCategory(String category);


    /**
     * Removes a media category from the database.
     * @param category name of the category to be deleted
     * @return
     */
    String deleteCategory(String category);


    /**
     * Adds a medium to a specified category.
     * @param medium to be added to a certain category
     * @param category to add the media to
     * @return
     */
    String addMediumToCategory(String medium, String category);


    /**
     * Moves a medium from one media category to another.
     * @param medium name of the medium to be moved
     * @param category to which the media should be moved
     * @return
     */
    String moveMediumToAnotherCategory(String medium, String category);


    /**
     * Deletes a selected medium from the database.
     * @param media name of the medium to be deleted
     * @return
     */
    String deleteMediumFromCollection(String media);


    /**
     * Searches for media that belong to a certain category.
     * @param category to which the media belong
     * @return
     */
    String searchMediaByCategory(String category);


    /**
     * Searches for media with the given attribute.
     * @param attribute common for the searched media
     * @return
     */
    String searchMediaByAttribute(String attribute);


}
