package nl.han.oose.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import nl.han.oose.ConnectionFactory;
import nl.han.oose.dto.Token;
import org.bson.Document;

import javax.inject.Inject;

public class TokenDAO {

    @Inject
    private ConnectionFactory connectionFactory;

    public Token createNewToken(String token, String username) {
        MongoCollection<Document> collection = connectionFactory.getDatabase().getCollection("token");
        Document newToken = new Document("token", token).append("username", username);
        collection.insertOne(newToken);
        Token userToken = new Token(token, username);
        return userToken;
    }


    public boolean tokenValidation(Token userToken) {
        MongoCollection<Document> collection = connectionFactory.getDatabase().getCollection("token");
        BasicDBObject query = new BasicDBObject();
        query.put("token", userToken.getToken());
        query.put("username", userToken.getUser());
        return isInDatabase(collection.find(query).iterator());
    }


    public boolean checkToken(String username) {
        MongoCollection<Document> collection = connectionFactory.getDatabase().getCollection("token");
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        return isInDatabase(collection.find(query).iterator());
    }

    private boolean isInDatabase(MongoCursor<Document> query){
        try {
            if (query.hasNext()){
                return true;
            }
        } finally {
            query.close();
        }
        return false;
    }

    public Token getTokenObject(String token) {
        MongoCollection<Document> collection = connectionFactory.getDatabase().getCollection("token");
        BasicDBObject query = new BasicDBObject();
        query.put("token", token);
        MongoCursor<Document> result = collection.find(query).iterator();
        Document queryResult = result.next();
        Token userToken = new Token(token, queryResult.get("username").toString());
        return userToken;
    }

    public Token retrieveToken(String username) {
        MongoCollection<Document> collection = connectionFactory.getDatabase().getCollection("token");
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        MongoCursor<Document> result = collection.find(query).iterator();
        Document queryResult = result.next();
        Token userToken = new Token(queryResult.get("token").toString(), username);
        return userToken;
    }

}
