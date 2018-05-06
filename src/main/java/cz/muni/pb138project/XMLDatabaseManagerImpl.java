package cz.muni.pb138project;

import javax.xml.xquery.*;
import java.util.Map;

/**
 * @author Lubos Lahucky
 */
public class XMLDatabaseManagerImpl implements XMLDatabaseManager {
    private XQConnection connection;

    XMLDatabaseManagerImpl(XQDataSource xqDataSource) throws XQException {
        connection = xqDataSource.getConnection();
    }

    public void closeConnection() throws XQException {
        connection.close();
    }

    private void updateQuery(String query) throws XQException {
        XQPreparedExpression expression = connection.prepareExpression(query);
        expression.executeQuery();
    }

    @Override
    public void createCategory(String category) throws XQException {
        updateQuery("update insert " + category + " into doc('database.xml')/collection");
    }

    @Override
    public void deleteCategory(String category) throws XQException {
        updateQuery("update delete doc('database.xml')/collection/category[@name=" + category + "]");
    }

    @Override
    public String searchMediaByCategory(String category) throws XQException {
        String query = "<media>" +
                "{" +
                "for $medium in doc('database.xml')/collection/category/medium[../@name=" + category + "] " +
                "return $medium" +
                "}" +
                "</media>";
        XQPreparedExpression expression = connection.prepareExpression(query);
        return expression.executeQuery().getSequenceAsString(null);
    }

    @Override
    public String findAllCategories() throws XQException {
        String query = "<categories>" +
                "{" +
                "for $category in doc('database.xml')/collection/category " +
                "return <category>{data($category/@name)}</category>" +
                "}" +
                "</categories>";
        XQPreparedExpression expression = connection.prepareExpression(query);
        return expression.executeQuery().getSequenceAsString(null);
    }

    @Override
    public void addMediumToCollection(String medium, String category) throws XQException {
        updateQuery("update insert " + medium + " into doc('database.xml')/collection/category[@name=" + category + "]");
    }

    @Override
    public void moveMediumToAnotherCategory(int mediumId, String category) {
        // TODO
    }

    @Override
    public void deleteMediumFromCollection(String medium) throws XQException {
        updateQuery("update delete doc('database.xml')//medium[@name=" + medium + "]");
    }

    @Override
    public String searchMedia(String label, String[] genres, Map<String, String> properties, String category) {
        // TODO
        return null;
    }
}
