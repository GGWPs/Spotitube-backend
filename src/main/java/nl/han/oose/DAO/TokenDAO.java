package nl.han.oose.DAO;

import nl.han.oose.ConnectionFactory;
import nl.han.oose.dto.Token;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class TokenDAO {

    @Inject
    private ConnectionFactory connectionFactory;

    public Token createNewToken(String username, String token) {
        Token userToken;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO token (token, username) VALUES (?,?)")) {
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);
            preparedStatement.execute();

            userToken = new Token(token, username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userToken;
    }


    public boolean tokenValidation(Token userToken) {
        boolean isValid = false;
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM token WHERE token = ? AND username = ?");
        ) {
            preparedStatement.setString(1, userToken.getToken());
            preparedStatement.setString(2, userToken.getUser());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isValid;
    }


    public boolean checkToken(String username) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement getTokenStatement = connection.prepareStatement("SELECT * FROM token WHERE username = ?")) {
            getTokenStatement.setString(1, username);
            ResultSet resultSet = getTokenStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Token getTokenObject(String token) {
        Token userToken = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement getTokenStatement = connection.prepareStatement("SELECT * FROM token WHERE token = ?")) {
            getTokenStatement.setString(1, token);
            ResultSet resultSet = getTokenStatement.executeQuery();
            while (resultSet.next()) {
                String tokenString = resultSet.getString("token");
                String user = resultSet.getString("username");
                userToken = new Token(tokenString, user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userToken;
    }

    public Token retrieveToken(String username) {
        Token userToken = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement tokenStatement = connection.prepareStatement("SELECT * FROM token WHERE username = ?")) {
            tokenStatement.setString(1, username);
            ResultSet resultSet = tokenStatement.executeQuery();
            while (resultSet.next()) {
                String tokenString = resultSet.getString("token");
                String user = resultSet.getString("username");
                userToken = new Token(tokenString, user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userToken;
    }
}
