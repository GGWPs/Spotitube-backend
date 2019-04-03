package nl.han.oose;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private Properties properties;

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
        if (properties == null) {
            properties = readProperties();
        }
        try {
            return DriverManager.getConnection(properties.getProperty("connectionUrl"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
