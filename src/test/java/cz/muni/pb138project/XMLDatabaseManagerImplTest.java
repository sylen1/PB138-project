package cz.muni.pb138project;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xmldb.api.base.XMLDBException;

import static org.junit.Assert.*;

public class XMLDatabaseManagerImplTest {
    private XMLDatabaseManagerImpl xmlDatabaseManager;

    @Before
    public void setUp() throws Exception {
        xmlDatabaseManager = new XMLDatabaseManagerImpl("xmldb:exist://localhost:8080/exist/xmlrpc/db",
                "admin",
                "",
                "test-database.xml");
    }

    @After
    public void tearDown() throws Exception {
        xmlDatabaseManager.closeConnection();
    }

    @Test
    public void createCategory() throws XMLDBException {
        xmlDatabaseManager.createCategory("newCategory");
    }

    @Test
    public void deleteCategory() throws XMLDBException {
        xmlDatabaseManager.deleteCategory("NeWCaTEGORy");
    }

    @Test
    public void findAllCategories() throws XMLDBException {
        System.out.println(xmlDatabaseManager.findAllCategories());
    }

    @Test
    public void findAllCategoriesWithCounts() throws XMLDBException {
        System.out.println(xmlDatabaseManager.findAllCategoriesWithCounts());
    }

    @Test
    public void searchMediaByCategory() throws XMLDBException {
        System.out.println(xmlDatabaseManager.searchMediaByCategory("MoViEs"));
    }

    @Test
    public void getFirstCategory() throws XMLDBException {
        System.out.println(xmlDatabaseManager.getFirstCategory());
    }

    @Test
    public void deleteMediumFromCollection() throws XMLDBException {
        xmlDatabaseManager.deleteMediumFromCollection("8");
    }

    @Test
    public void addMediumToCollection() throws XMLDBException {
        String medium = "<medium> " +
                "<id>8</id> " +
                "<label>Tentacles (Tentacoli)</label> " +
                "<type>DVD</type> " +
                "<genres> " +
                "<genre>Horror</genre> " +
                "<genre>Sci-Fi</genre> " +
                "<genre>Thriller</genre> " +
                "</genres> " +
                "</medium>";
        xmlDatabaseManager.addMediumToCollection(medium, "NEWcAtegOrY");
    }

    @Test
    public void moveMediumToAnotherCategory() throws XMLDBException {
        xmlDatabaseManager.moveMediumToAnotherCategory("8", "MOviES");
    }

    // ... and other tests
}