package cz.muni.pb138project;

import javax.xml.xquery.*;
import java.util.Map;

/**
 * @author Lubos Lahucky
 */
public class XMLDatabaseManagerImpl implements XMLDatabaseManager {
    private final XQConnection connection;
    private final String doc;

    XMLDatabaseManagerImpl(XQDataSource xqDataSource, String document) throws XQException {
        connection = xqDataSource.getConnection();
        doc = document;
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
        updateQuery("update insert " + category.toLowerCase() + " into doc(" + doc + ")/collection");
    }

    @Override
    public void deleteCategory(String category) throws XQException {
        updateQuery("update delete doc(" + doc + ")/collection/category[@name=" + category.toLowerCase() + "]");
    }

    @Override
    public String searchMediaByCategory(String category) throws XQException {
        String query = "<media>" +
                "{" +
                "for $medium in doc(" + doc + ")/collection/category/medium[../@name=" + category.toLowerCase() + "] " +
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
                "for $category in doc(" + doc + ")/collection/category " +
                "return <category>{data($category/@name)}</category>" +
                "}" +
                "</categories>";
        XQPreparedExpression expression = connection.prepareExpression(query);
        return expression.executeQuery().getSequenceAsString(null);
    }

    @Override
    public String findAllCategoriesWithCounts() throws XQException {
        String query = "<categories>" +
                "{" +
                "for $category in doc(" + doc + ")/collection/category " +
                "return " +
                "<category>" +
                "<name>{data($category/@name)}</name>" +
                "<count>{count($category/medium)}</count>" +
                "</category>" +
                "}" +
                "</categories>";
        XQPreparedExpression expression = connection.prepareExpression(query);
        return expression.executeQuery().getSequenceAsString(null);
    }

    @Override
    public void addMediumToCollection(String medium, String category) throws XQException {
        updateQuery("update insert " + medium.toLowerCase() +
                " into doc(" + doc + ")/collection/category[@name=" + category.toLowerCase() + "]");
    }

    @Override
    public void moveMediumToAnotherCategory(int mediumId, String category) {
        // TODO
    }

    @Override
    public void deleteMediumFromCollection(String medium) throws XQException {
        updateQuery("update delete doc(" + doc + ")//medium[@name=" + medium.toLowerCase() + "]");
    }

    @Override
    public String searchMedia(String label, String[] genres, Map<String, String> properties, String category) {
        // TODO
        return null;
    }
}
