package application.Graphics.FXML;


import application.Graphics.item.scenes.GameScene;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

import static java.lang.Math.abs;

public class PlayPaneController {

    private MediaPlayer mediaPlayer;
    private Media media;
    //da eliminare dopo integrazione database
    private final String songsPath = "src/trackanalyzer/songs";
    private ObservableList<String> songs;
    

    @FXML
    private BorderPane playBorderPane;

    @FXML
    private Button playButton;

    @FXML
    private Button backButton;

    @FXML
    private Button uploadSongButton;

    @FXML
    private ListView<String> songsListView;

    @FXML
    public void initialize() {
        songs = FXCollections.observableArrayList(new File(songsPath).list());
        songsListView.setItems(songs);

        //gestione file audio
        songsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                media = new Media(Paths.get(songsPath + "/" + songsListView.getSelectionModel().getSelectedItem()).toUri().toString());
                if (mediaPlayer != null) {
                    mediaPlayer.dispose();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setStartTime(Duration.millis(90000));
                mediaPlayer.play();
            }
        });
    }

    @FXML
    public void playButtonClicked(ActionEvent event) {
        ((Stage) playBorderPane.getParent().getScene().getWindow()).close();
        System.out.println("Let's go!");
        mediaPlayer.dispose();
        Stage playStage = new Stage();
        playStage.setMinWidth(1200);
        playStage.setMinHeight(800);
        Media selectedSong = new Media(Paths.get(songsPath + "/" + songsListView.getSelectionModel().getSelectedItem()).toUri().toString());
        playStage.setScene(new GameScene(new AnchorPane(), playStage.getMinWidth(), playStage.getMinHeight(), selectedSong));
        playStage.setTitle("RythmUp");
        playStage.show();
    }

    @FXML
    public void uploadSongButtonClicked(ActionEvent event) throws IOException, InterruptedException {
        List<File> fileList = new FileChooser().showOpenMultipleDialog(playBorderPane.getScene().getWindow());
        if (fileList != null) {
            for (File file : fileList) {
                String correctName;
                if (file.getName().contains(" "))
                    correctName = file.getName().replace(" ", "");
                else
                    correctName = file.getName();
                Path filename = Paths.get(songsPath + "/" + correctName);
                File copyFile = new File(filename.toUri());
                try {
                    copyFile.createNewFile();
                    FileInputStream source = new FileInputStream(file);
                    FileOutputStream destination = new FileOutputStream(copyFile);
                    int lenght = (int) file.length();
                    byte[] buffer = new byte[lenght];
                    source.read(buffer);
                    destination.write(buffer, 0, lenght);


                    MongoClientURI uri = new MongoClientURI(
                            "mongodb+srv://base-user:RythmUp@rythmup-v9vh2.gcp.mongodb.net/test?retryWrites=true&w=majority");

                    MongoClient mongoClient = new MongoClient(uri);
                    MongoDatabase database = mongoClient.getDatabase("MusicDatabase");
                    List<String> collectionList = new ArrayList<String>();
                    database.listCollectionNames().forEach((Block<? super String>) a -> collectionList.add(a));
                    if (collectionList.contains(file.getName())) {
                        System.out.println("Song already available: " + file.getName());
                    } else {
                        database.createCollection(correctName);
                        MongoCollection collection = database.getCollection(correctName);
                        Process proc =  Runtime.getRuntime().exec(" java -jar src/trackanalyzer/TrackAnalyzer.jar " + copyFile.getCanonicalPath());
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                        String line = stdInput.readLine();
                        System.out.println(line);
                        double bpm = Double.parseDouble(line.split("BPM: ")[1]);
                        System.out.println("BPM VALUE: "+ bpm);
                        Document doc = new Document("songFile", buffer)
                                .append("bpm", bpm);
                        collection.insertOne(doc);

                        FindIterable<Document> x = collection.find();
                        doc = x.first();
                        Binary bin = (Binary) doc.get("songFile");
                        double bpmx = (Double) doc.get("bpm");
                        System.out.println("BPM OBTAINED FROM DB: " + bpmx);
                        buffer = bin.getData();
                        File test = new File("test.mp3");
                        FileOutputStream outputStream = new FileOutputStream(test);
                        outputStream.write(buffer, 0, buffer.length);
                    }

                    /*Document doc = new Document("songName", file.getName())
                            .append("songFile", buffer)
                            .append("bpm", 100.0);
                    collection.insertOne(doc);*/
                    mongoClient.close();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (songsListView.getItems().contains(copyFile.getName())) {
                    System.out.println("This song is already available" + copyFile.getName());
                    continue;
                } else {
                    songs.add(copyFile.getName());
                    songs.sort((a, b) -> a.compareTo(b));
                }
            }
                    } else {
                    System.out.println("Invalid or no file detected");
                    }
    }

    @FXML
    public void backButtonClicked(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }
        ((Pane) playBorderPane.getParent()).getChildren().remove(playBorderPane);
    }

}
