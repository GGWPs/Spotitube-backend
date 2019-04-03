package nl.han.oose.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import nl.han.oose.ConnectionFactory;
import org.bson.Document;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.IntStream;


public class AccountDAO {

    private ConnectionFactory connectionFactory;

    public AccountDAO() {
        connectionFactory = new ConnectionFactory();
    }

        public boolean accountValidation(String username, String password) {
                MongoCollection<Document> collection = connectionFactory.getDatabase().getCollection("account");
                BasicDBObject query = new BasicDBObject();
                query.put("username", username);
                query.put("password", password);
                MongoCursor<Document> cursor = collection.find(query).iterator();
                try {
                    while (cursor.hasNext()) {
                        return true;
                    }
                } finally {
                    cursor.close();
                }
            return false;
        }
}
