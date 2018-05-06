package cz.muni.pb138project;

import net.xqj.exist.ExistXQDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

import static org.junit.Assert.*;

public class XMLDatabaseManagerImplTest {
    private XMLDatabaseManagerImpl xmlDatabaseManager;

    @Before
    public void setUp() throws Exception {
        XQDataSource xqs = new ExistXQDataSource();
        xqs.setProperty("serverName", "localhost");
        xqs.setProperty("port", "8080");
        xmlDatabaseManager = new XMLDatabaseManagerImpl(xqs);
    }

    @After
    public void tearDown() throws Exception {
        xmlDatabaseManager.closeConnection();
    }

    @Test
    public void createCategory() {
    }

    // ... and other tests
}