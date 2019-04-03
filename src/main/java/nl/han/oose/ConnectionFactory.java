package nl.han.oose;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private Properties properties;
    MongoClient mongoClient;

    public ConnectionFactory() {
        if (properties == null) {
            properties = readProperties();
        }
        if(mongoClient == null){
            mongoClient = new MongoClient("localhost", 27017);
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

    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase("spotitube");
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
