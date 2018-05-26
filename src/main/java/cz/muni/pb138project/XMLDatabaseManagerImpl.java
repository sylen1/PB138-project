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

    XMLDatabaseManagerImpl(String URI, String user, String password, String document) throws XMLDBException {
        try {
            Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
            Database database = (Database) cl.newInstance();
            DatabaseManager.registerDatabase(database);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

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
                "for $medium in " +
                "doc('" + doc + "')/collection/category/medium[../lower-case(@name)=lower-case('" + category + "')] " +
                // "order by ($medium/id + 0)" +
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
        String query = "update insert " + medium +
                "into doc('" + doc + "')/collection/category[lower-case(@name)=lower-case('" + category + "')]";
        updateQuery(query);
    }

    @Override
    public void moveMediumToAnotherCategory(String mediumId, String category) throws XMLDBException {
        String medium = selectQuery("doc('" + doc + "')//medium[id=" + mediumId + "]");
        deleteMediumFromCollection(mediumId + "");
        addMediumToCollection(medium, category);
    }

    @Override
    public void deleteMediumFromCollection(String mediumId) throws XMLDBException {
        updateQuery("update delete doc('" + doc + "')//medium[id=" + mediumId + "]");
    }

    @Override
    public String searchMedia(String label, String[] genres, Map<String, String> properties, String category) throws XMLDBException {
        if (label == null)
            label = "";

        String query = "let $properties := <properties>";
        if (properties != null) {
            StringBuilder queryBuilder = new StringBuilder(query);
            for (Map.Entry<String, String> property: properties.entrySet()) {
                queryBuilder.append("<").append(property.getKey()).append(">")
                        .append(property.getValue())
                        .append("</").append(property.getKey()).append(">");
            }
            query = queryBuilder.toString();
        }
        query = query + "</properties>" +
                "let $genres := <genres>";

        if (genres != null) {
            StringBuilder queryBuilder = new StringBuilder(query);
            for (String genre : genres) {
                queryBuilder.append("<genre>").append(genre).append("</genre>");
            }
            query = queryBuilder.toString();
        }

        query = query + "</genres> " +
                "return " +
                "<media>" +
                "{" +
                "for $category in doc('" + doc + "')/collection/category ";

        if (category != null) {
            query = query + "where fn:lower-case($category/@name) = '" + category.toLowerCase() + "' ";
        }

        query = query + "return " +
                "for $medium in $category/medium " +
                "return " +
                "if (fn:contains(fn:lower-case($medium/label), '" + label.toLowerCase() + "') " +
                "and (every $genre in $genres/genre/text() " +
                "satisfies fn:lower-case($genre) = $medium/genres/genre/fn:lower-case(text()))" +
                "and (every $property in $properties/* " +
                "satisfies (fn:lower-case($property/name()) = $medium/properties/*/fn:lower-case(name())" +
                "and fn:lower-case($property/text()) = $medium/properties/*/fn:lower-case(text())))" +
                ") " +
                "then $medium " +
                "else ()" +
                "}" +
                "</media>";

        //every $genre in $genres/genre/text() satisfies $genre = medium/genres/genre/text()


        return selectQuery(query);
    }

    @Override
    public String getFirstCategory() throws XMLDBException {
        String query = "doc('" + doc + "')/collection/category[1]/data(@name)";
        return selectQuery(query);
    }
}
