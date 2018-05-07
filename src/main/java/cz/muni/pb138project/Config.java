package cz.muni.pb138project;

import net.xqj.exist.ExistXQDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

@Configuration
public class Config {
    @Bean
    public XQDataSource xqDataSource() {
        ExistXQDataSource xqs = new ExistXQDataSource();
        xqs.setProperty("serverName", "localhost");
        xqs.setProperty("port", "8080");
        xqs.setUser("admin");
        xqs.setPassword("");
        return xqs;
    }

    @Bean
    public XMLDatabaseManager databaseManager() throws XQException {
        return new XMLDatabaseManagerImpl(xqDataSource(), "database.xml");
    }
}
