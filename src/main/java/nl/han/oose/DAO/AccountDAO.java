package nl.han.oose.DAO;

import nl.han.oose.objects.Account;
import nl.han.oose.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private ConnectionFactory connectionFactory;

    public AccountDAO() {
        connectionFactory = new ConnectionFactory();
    }

    public List<Account> getAllAcounts() {
        List<Account> accounts = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM account")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String user = resultSet.getString("username");
                String password = resultSet.getString("password");
                accounts.add(new Account(user, password));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }
}
