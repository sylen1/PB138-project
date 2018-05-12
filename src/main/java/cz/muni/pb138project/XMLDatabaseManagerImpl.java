package cz.muni.pb138project;


import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;

import java.util.Map;

/**
 * @author Lubos Lahucky
 */
public class XMLDatabaseManagerImpl implements XMLDatabaseManager {
    private final Collection collection;
    private final XQueryService queryService;
    private final String doc;

    XMLDatabaseManagerImpl(String URI, String user, String password, String document) throws Exception {
        Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
        Database database = (Database)cl.newInstance();
        DatabaseManager.registerDatabase(database);

        collection = DatabaseManager.getCollection(URI, user, password);
        queryService = (XQueryService) collection.getService("XQueryService", "1.0");
        doc = document;
    }

    public void closeConnection() throws XMLDBException {
        if (collection != null) {
            collection.close();
        }
    }

    private void updateQuery(String query) throws XMLDBException {
        CompiledExpression expression = queryService.compile(query);
        queryService.execute(expression);
    }

    private String selectQuery(String query) throws XMLDBException {
        CompiledExpression expression = queryService.compile(query);
        ResourceSet result = queryService.execute(expression);
        ResourceIterator i = result.getIterator();
        StringBuilder builder = new StringBuilder();
        Resource res;
        while (i.hasMoreResources()) {
            res = i.nextResource();
            builder.append(res.getContent());
        }
        return builder.toString();
    }

    @Override
    public void createCategory(String category) throws XMLDBException {
        updateQuery("update insert <category name='" + category + "' /> into doc('" + doc + "')/collection");
    }

    @Override
    public void deleteCategory(String category) throws XMLDBException {
        updateQuery("update delete doc('" + doc + "')/collection/category[lower-case(@name)=lower-case('" + category + "')]");
    }

    @Override
    public String searchMediaByCategory(String category) throws XMLDBException {
        String query = "<media>" +
                "{" +
                "for $medium in doc('" + doc + "')/collection/category/medium[../lower-case(@name)=lower-case('" + category + "')] " +
                "return $medium" +
                "}" +
                "</media>";
        return selectQuery(query);
    }

    @Override
    public String findAllCategories() throws XMLDBException {
        String query = "<categories>" +
                "{" +
                "for $category in doc('" + doc + "')/collection/category " +
                "return <category>{data($category/@name)}</category>" +
                "}" +
                "</categories>";
        return selectQuery(query);
    }

    @Override
    public String findAllCategoriesWithCounts() throws XMLDBException {
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
        return selectQuery(query);
    }

    @Override
    public void addMediumToCollection(String medium, String category) throws XMLDBException {
        // TODO
    }

    @Override
    public void moveMediumToAnotherCategory(int mediumId, String category) {
        // TODO
    }

    @Override
    public void deleteMediumFromCollection(String medium) throws XMLDBException {
        updateQuery("update delete doc('" + doc + "')//medium[lower-case(@name)=lower-case('" + medium + "')]");
    }

    @Override
    public String searchMedia(String label, String[] genres, Map<String, String> properties, String category) throws XMLDBException {
        // TODO
        return null;
    }

    @Override
    public String getFirstCategory() throws XMLDBException {
        String query = "doc('" + doc + "')/collection/category[1]/data(@name)";
        return selectQuery(query);
    }
}
