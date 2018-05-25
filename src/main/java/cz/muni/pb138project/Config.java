package cz.muni.pb138project;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xmldb.api.base.XMLDBException;

@Configuration
public class Config {

    @Value("${exist.port}")
    private String port;

    @Value("${exist.username}")
    private String username;

    @Value("${exist.password}")
    private String password;

    @Value("${exist.document}")
    private String document;

    @Bean
    public XMLDatabaseManager databaseManager() throws XMLDBException {
        return new XMLDatabaseManagerImpl("xmldb:exist://localhost:" + port + "/exist/xmlrpc/db",
                username,
                password,
                document);
    }
}
