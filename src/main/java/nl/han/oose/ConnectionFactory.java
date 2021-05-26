package nl.han.oose;

import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private Properties properties;
    private Driver driver;

    public ConnectionFactory() {
        if (properties == null) {
            properties = readProperties();
        }
        try {
            Class.forName(properties.getProperty("pathToDriver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(properties.getProperty("connectionUrl"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Driver getNeo4JConnection() {
        String uri = "bolt://localhost:7687";
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "mynewpass"));
        return driver;
    }

    private Properties readProperties() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
