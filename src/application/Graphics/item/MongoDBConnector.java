package application.Graphics.item;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MongoDBConnector {

    private final String DB_AUTH_STRING = "mongodb+srv://base-user:RythmUp@rythmup-v9vh2.gcp.mongodb.net/test?retryWrites=true&w=majority";
    private MongoClientURI uri;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection collection;

    public MongoDBConnector() {
        this.uri = new MongoClientURI(DB_AUTH_STRING);
        this.mongoClient = new MongoClient(uri);
        this.database = mongoClient.getDatabase("MusicDatabase");
    }

    public void closeConnection() {
        mongoClient.close();
    }

    public MongoCollection getCollection(String songName) {
        return database.getCollection(songName);
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

    public File downloadSong(String songDir, String songName) {
        File tmp = null;
        String songPath = songDir + songName;
        collection = getCollection(songName);
        try {
            FindIterable<Document> docList = collection.find();
            Document doc = docList.first();
            Binary bin = (Binary) doc.get("songFile");
            byte[] buffer = bin.getData();
            tmp = new File(songPath);
            tmp.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(tmp);
            outputStream.write(buffer, 0, buffer.length);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return tmp;
    }

    public void uploadSong(String songName, byte[] binarySongFile, double bpm) {
        database.createCollection(songName);
        collection = getCollection(songName);
        Document doc = new Document("songFile", binarySongFile)
                .append("bpm", bpm);
        collection.insertOne(doc);
    }

    public double getBPM(String songName) {
        collection = getCollection(songName);
        FindIterable<Document> docList = collection.find();
        double bpm = (Double) docList.first().get("bpm");
        return bpm;
    }

    public List<String> populateSongList() {
        List<String> songList = new ArrayList<String>();
        this.database.listCollectionNames().forEach((Block<? super String>) a -> songList.add(a));
        return songList;
    }
}
