package cz.muni.pb138project;

import java.util.Map;

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
     * Searches for media that belong to a certain category.
     * @param category to which the media belong
     * @return
     */
    String searchMediaByCategory(String category);


    /**
     * Creates a new medium entry and adds it to a given category in the database.
     * @param medium to be added to a certain category
     * @param category to add the media to
     * @return
     */
    String addMediumToCollection(String medium, String category);


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
     * Searches for media with the given attribute.
     * @param label
     * @param genres to which the media belong
     * @param properties
     * @param category of the searched media
     * @return
     */
    String searchMedia(String label, String[] genres, Map<String, String>properties, String category);


}
