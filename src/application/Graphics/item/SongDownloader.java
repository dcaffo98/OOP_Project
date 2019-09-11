package application.Graphics.item;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class SongDownloader {
    private MongoDatabase database;
    private MongoCollection collection;
    private String songName;
    private String mediaPathString;
    private File tempSongFile;

    public SongDownloader(MongoDatabase database, String songName) {
        this.database = database;
        this.collection = database.getCollection(songName);
        this.songName = songName;
        this.tempSongFile = downloadSong();
        this.mediaPathString = Paths.get(tempSongFile.getPath()).toUri().toString();
    }

    public String getMediaPathString() {
        return mediaPathString;
    }

    public File downloadSong() {
        File tmp = null;
        try {
            FindIterable<Document> docList = collection.find();
            Document doc = docList.first();
            Binary bin = (Binary) doc.get("songFile");
            double bpmx = (Double) doc.get("bpm");
            System.out.println("BPM OBTAINED FROM DB: " + bpmx);
            byte[] buffer = bin.getData();
            tmp = new File(songName);
            FileOutputStream outputStream = new FileOutputStream(tmp);
            outputStream.write(buffer, 0, buffer.length);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return tmp;
    }
}
