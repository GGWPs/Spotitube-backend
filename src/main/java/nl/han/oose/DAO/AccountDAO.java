package nl.han.oose.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import nl.han.oose.ConnectionFactory;
import org.bson.Document;



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
