package cz.muni.pb138project;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
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
        // XQJ does not support insert?
        updateQuery("update insert <category name='" + category + "' /> into doc('" + doc + "')/collection");
    }

    @Override
    public void deleteCategory(String category) throws XQException {
        // XQJ does not support delete?
        updateQuery("update delete doc('" + doc + "')/collection/category[lower-case(@name)=lower-case('" + category + "')]");
    }

    @Override
    public String searchMediaByCategory(String category) throws XQException {
        String query = "<media>" +
                "{" +
                "for $medium in doc('" + doc + "')/collection/category/medium[../lower-case(@name)=lower-case('" + category + "')] " +
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
                "for $category in doc('" + doc + "')/collection/category " +
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
                "for $category in doc('" + doc + "')/collection/category " +
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
        // TODO
    }

    @Override
    public void moveMediumToAnotherCategory(int mediumId, String category) {
        // TODO
    }

    @Override
    public void deleteMediumFromCollection(String medium) throws XQException {
        // XQJ does not support delete?
        updateQuery("update delete doc('" + doc + "')//medium[lower-case(@name)=lower-case('" + medium + "')]");
    }

    @Override
    public String searchMedia(String label, String[] genres, Map<String, String> properties, String category) throws XQException {
        // TODO
        return null;
    }

    @Override
    public String getFirstCategory() throws XQException {
        return null;
    }
}
