package nl.han.oose.DAO;

import nl.han.oose.ConnectionFactory;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.neo4j.driver.Values.parameters;


public class AccountDAO {

    private ConnectionFactory connectionFactory;

    public AccountDAO() {
        connectionFactory = new ConnectionFactory();
    }

        public boolean accountValidation(String username, String password) {
            try (Session session = connectionFactory.getNeo4JConnection().session() )
            {
                String user = session.writeTransaction(new TransactionWork<String>() {
                    @Override
                    public String execute(Transaction transaction) {
                        Result result = transaction.run( "MATCH (a:Account {username: $username, password: $password}) RETURN a.username",
                                parameters( "username", username,"password", password ));
                        return result.single().get( 0 ).asString();
                    }
                });

                return user != null;
            }
        }
}
