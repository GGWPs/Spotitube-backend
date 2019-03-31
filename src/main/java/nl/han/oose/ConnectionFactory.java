package nl.han.oose;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static final String pathToDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=spotitube;user=sa;password=a119af9915";

    public ConnectionFactory() {
        try {
            Class.forName(pathToDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
