package application.Graphics.item;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.util.Pair;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/*
questa classe si occupa di gestire la comunicazione col database MongoDB utilizzando l'apposito driver Java
 */

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

    /*
    scarica la canzone "songName" come file binario dal database e la inserisce nella cartella locale "songDir"
     */
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

    /*
    crea una nuova collection sul db
     */
    public void uploadSong(String songName, byte[] binarySongFile, double bpm) {
        database.createCollection(songName);
        collection = getCollection(songName);
        Document doc = new Document("songFile", binarySongFile)
                .append("bpm", bpm)
                .append("results", new ArrayList<>());
        collection.insertOne(doc);
    }

    /*
    query al db per ottenere i bpm di una canzone
     */
    public double getBPM(String songName) {
        collection = getCollection(songName);
        FindIterable<Document> docList = collection.find();
        double bpm = (Double) docList.first().get("bpm");
        return bpm;
    }

    /*
    richiamato dal PlayPaneController per inizializzare gli elementi da visualizzare nella ListView
     */
    public List<String> populateSongList() {
        List<String> songList = new ArrayList<String>();
        database.listCollectionNames().forEach((Block<? super String>) a -> songList.add(a));
        return songList;
    }

    /*
    upload sul db del risultato della partita
     */
    public void uploadResult(String songName, String username, int score) {
        collection = getCollection(songName);
        FindIterable<Document> docList = collection.find();
        Document doc = docList.first();
        Bson updatedValue = new Document("results", new Document(username,score));
        Bson updateOperation = new Document("$push", updatedValue);
        collection.updateOne(doc, updateOperation);
    }

    /*
    query per ottenere tutti i risultati di una determinata canzone
     */
    public ArrayList<Pair<String, Integer>> downloadSongScore (String songName) {
        collection = getCollection(songName);
        ArrayList<Pair<String,Integer>> scores = new ArrayList<Pair<String,Integer>>();
        List<Document> docList = (List<Document>) collection.find().into(new ArrayList<Document>());
        for (Document songData : docList) {
            List<Document> results = (List<Document>) songData.get("results");
            for (Document result : results) {
                Iterator<String> iterator = result.keySet().iterator();
                String name = iterator.next();
                Iterator<Object> valueIterator = result.values().iterator();
                Integer value = (Integer)valueIterator.next();
                scores.add(new Pair<>(name,value));
            }
        }
        return scores;
    }

    /*
    query per ottenere il massimo punteggio ottenuto per ogni canzone
     */
    public ArrayList<HighScoreTableRow> downloadHighScore () {
        ArrayList<HighScoreTableRow> highScores = new ArrayList<HighScoreTableRow>();
        List<MongoCollection> collectionList = new ArrayList<MongoCollection>();
        database.listCollectionNames().forEach((Block<? super String>) a -> collectionList.add(getCollection(a)));
        ArrayList<Pair<String,Integer>> scores = new ArrayList<Pair<String,Integer>>();
        for(MongoCollection col : collectionList) {
            List<Document> docList = (List<Document>) col.find().into(new ArrayList<Document>());
            String colName = col.getNamespace().getCollectionName();
            for (Document songData : docList) {
                List<Document> results = (List<Document>) songData.get("results");
                for (Document result : results) {
                    Iterator<String> iterator = result.keySet().iterator();
                    String name = iterator.next();
                    Iterator<Object> valueIterator = result.values().iterator();
                    Integer value = (Integer)valueIterator.next();
                    scores.add(new Pair<>(name,value));
                }
                scores.sort((a,b) -> a.getValue().compareTo(b.getValue()));
                Collections.reverse(scores);
                highScores.add(new HighScoreTableRow(colName,scores.get(0).getKey(),scores.get(0).getValue()));
            }

        }
        return highScores;
    }
}
