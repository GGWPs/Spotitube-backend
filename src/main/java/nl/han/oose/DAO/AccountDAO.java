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

    public AccountDAO(){connectionFactory = new ConnectionFactory();
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

    public List<Account> getAllAccounts() {
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


    public Account getAccount(String username) {
        Account account = null;
        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement getAccountStatement = connection.prepareStatement("SELECT * FROM ACCOUNT WHERE user = ?")) {
            getAccountStatement.setString(1, username);
            ResultSet accountResult = getAccountStatement.executeQuery();

            while (accountResult.next()) {
                String user = accountResult.getString("user");
                String password = accountResult.getString("password");

                account = new Account(user, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

}
