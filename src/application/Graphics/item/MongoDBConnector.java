package application.Graphics.item;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnector {

    private final String DB_AUTH_STRING = "mongodb+srv://base-user:RythmUp@rythmup-v9vh2.gcp.mongodb.net/test?retryWrites=true&w=majority";
    private MongoClientURI uri;
    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoDBConnector() {
        this.uri = new MongoClientURI(DB_AUTH_STRING);
        this.mongoClient = new MongoClient(uri);
        this.database = mongoClient.getDatabase("MusicDatabase");
    }

    public void closeConnection() {
        mongoClient.close();
    }

    public MongoClientURI getUri() {
        return uri;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

}
