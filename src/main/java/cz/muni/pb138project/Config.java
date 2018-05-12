package cz.muni.pb138project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

@Configuration
public class Config {

    @Bean
    public XMLDatabaseManager databaseManager() throws Exception {
        return new XMLDatabaseManagerImpl("xmldb:exist://localhost:8080/exist/xmlrpc/db",
                "admin",
                "",
                "database.xml");
    }
}
