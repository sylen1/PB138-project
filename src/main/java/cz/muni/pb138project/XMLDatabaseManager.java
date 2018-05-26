package cz.muni.pb138project;

import org.xmldb.api.base.XMLDBException;

import javax.xml.xquery.XQException;
import java.util.Map;

/**
 * Native XML database API manager.
 * @author Kristyna Pekarkova
 */
public interface XMLDatabaseManager {

    /**
     * Creates a new media category and adds it to the database.
     * @param category name of the category to be created
     */
    void createCategory(String category) throws XMLDBException;


    /**
     * Removes a media category from the database.
     * @param category name of the category to be deleted
     */
    void deleteCategory(String category) throws XMLDBException;


    /**
     * Searches for media that belong to a certain category.
     * @param category to which the media belong
     * @return all media that belong to one, specified category
     */
    String searchMediaByCategory(String category) throws XMLDBException;


    /**
     * Finds all media categories that currently exist in the database.
     * Example output:
     * <categories>
     *     <category>category1</category>
     *     <category>category2</category>
     *     <category>category3</category>
     *     ...
     * <categories>
     * @return all media categories in the database
     */
    String findAllCategories() throws XMLDBException;


    /**
     * Finds all media categories that currently exist in the database and their counts of media.
     * Example output:
     * <categories>
     *     <category>
     *         <name>category1</name>
     *         <count>4</count>
     *     </category>
     *     <category>
     *         <name>category1</name>
     *         <count>4</count>
     *     </category>
     *     ...
     * <categories>
     * @return all media categories in the database
     */
    String findAllCategoriesWithCounts() throws XMLDBException;


    /**
     * Creates a new medium entry and adds it to a given category in the database.
     * @param medium to be added to a certain category
     * @param category to add the media to
     */
    void addMediumToCollection(String medium, String category) throws XMLDBException;


    /**
     * Moves a medium from one media category to another.
     * @param mediumId id of the medium to be moved
     */
    void moveMediumToAnotherCategory(String mediumId, String category) throws XMLDBException;


    /**
     * Deletes a selected medium from the database.
     * @param mediaId id of the medium to be deleted
     */
    void deleteMediumFromCollection(String mediaId) throws XMLDBException;


    /**
     * Finds maximal value of medium id
     * @return string with maximum id value
     */
    String findMaximumMediaId() throws XMLDBException;

    /**
     * Searches for media with given attributes.
     * @param label
     * @param genres to which the media belong
     * @param properties
     * @param category of the searched media
     * @return
     */
    String searchMedia(String label, String[] genres, Map<String, String>properties, String category) throws XMLDBException;

    /**
     * Search for name of the first category in the database.
     * @return name of category
     */
    String getFirstCategory() throws XMLDBException;

}
