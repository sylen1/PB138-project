package cz.muni.pb138project;

import net.xqj.exist.ExistXQDataSource;

import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

import static org.junit.Assert.*;

/**
 * @author Lubos Lahucky
 */
public class XMLDatabaseManagerImplTest {
    private XMLDatabaseManagerImpl xmlDatabaseManager;

    @org.junit.Before
    public void setUp() throws Exception {
        XQDataSource xqs = new ExistXQDataSource();
        xqs.setProperty("serverName", "localhost");
        xqs.setProperty("port", "8080");
        xmlDatabaseManager = new XMLDatabaseManagerImpl(xqs);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        xmlDatabaseManager.closeConnection();
    }

    @org.junit.Test
    public void createCategory() {
    }

    @org.junit.Test
    public void deleteCategory() {
    }

    @org.junit.Test
    public void searchMediaByCategory() {
    }

    @org.junit.Test
    public void findAllCategories() throws XQException {
    }

    @org.junit.Test
    public void addMediumToCollection() {
    }

    @org.junit.Test
    public void moveMediumToAnotherCategory() {
    }

    @org.junit.Test
    public void deleteMediumFromCollection() {
    }

    @org.junit.Test
    public void searchMedia() {
    }
}