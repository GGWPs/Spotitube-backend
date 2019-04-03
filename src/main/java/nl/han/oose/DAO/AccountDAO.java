package nl.han.oose.DAO;

import nl.han.oose.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountDAO {

    private ConnectionFactory connectionFactory;

    public AccountDAO() {
        connectionFactory = new ConnectionFactory();
    }

        public boolean accountValidation(String username, String password) {
            try (Connection connection = connectionFactory.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM account WHERE username = ? AND password = ?")) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
}
