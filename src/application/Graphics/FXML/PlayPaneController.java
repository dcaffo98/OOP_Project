package application.Graphics.FXML;


import application.Graphics.item.MongoDBConnector;
import application.Graphics.item.SongDownloader;
import application.Graphics.item.scenes.GameScene;
import application.Graphics.item.scenes.MainScene;
import application.Graphics.item.stages.MainStage;
import com.mongodb.Block;
import com.mongodb.client.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
import javafx.util.Pair;
import org.bson.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

public class PlayPaneController {

    private MediaPlayer mediaPlayer;
    private Media media;
    private final String SONGS_PATH = "src/trackanalyzer/songs/";
    private String selectedSong;
    private ObservableList<String> songs;
    private MongoDBConnector mongoDBConnector;
    private File tmpSongFile;
    private double bpm;

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
        playBorderPane.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                if (newValue != null) {
                    //aggiungo tutti i nomi delle canzoni presenti sul database     ************************************
                    List<String> collectionList = mongoDBConnector.populateSongList();
                    songs = FXCollections.observableArrayList(collectionList);
                    songsListView.setItems(songs);
                }
            }
        });

        //quando una canzone è selezionata: se è presente in locale parte l'audio, se non lo è prima viene scaricata dal db         *****************
        songsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedSong = songsListView.getSelectionModel().getSelectedItem();
                if (selectedSong !=  null) {
                    if (Arrays.asList(new File(SONGS_PATH).list()).contains(selectedSong)) {
                        media = new Media(Paths.get(SONGS_PATH + selectedSong).toUri().toString());
                    } else {
                        tmpSongFile = mongoDBConnector.downloadSong(SONGS_PATH, selectedSong);
                        media = new Media(Paths.get(tmpSongFile.getPath()).toUri().toString());
                    }
                    bpm = mongoDBConnector.getBPM(selectedSong);
                    System.out.println(selectedSong + " BPM: " + bpm);

                    if (mediaPlayer != null) {
                        mediaPlayer.dispose();
                    }
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setStartTime(Duration.millis(90000));
                    mediaPlayer.play();
                }
            }
        });
    }

    @FXML
    public void playButtonClicked(ActionEvent event) {
        if (media != null && selectedSong != null) {
            MainStage mainStage = (MainStage) playBorderPane.getParent().getScene().getWindow();
            mainStage.hide();
            System.out.println("Let's go!");
            mediaPlayer.dispose();
            System.out.println(this.selectedSong + " BPM: " + bpm);
            Stage playStage = new Stage();
            playStage.setMinWidth(1080);
            playStage.setMinHeight(600);
            playStage.setScene(new GameScene(new AnchorPane(), playStage.getMinWidth(), playStage.getMinHeight(), media, bpm, mongoDBConnector, mainStage));
            playStage.setTitle("RythmUp");
            playStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select the song you want to play with");
            alert.showAndWait();
        }
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
                //controllo che la canzone non sia già presente                 *************************************
                if (songsListView.getItems().contains(correctName)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, correctName + " already available");
                    alert.showAndWait();
                    System.out.println("This song is already available" + correctName);
                    continue;
                }
                Path filePath = Paths.get(SONGS_PATH + correctName);
                File copyFile = new File(filePath.toUri());
                try {
                    //copio il file in locale               *********************************
                    copyFile.createNewFile();
                    FileInputStream source = new FileInputStream(file);
                    FileOutputStream destination = new FileOutputStream(copyFile);
                    int lenght = (int) file.length();
                    byte[] buffer = new byte[lenght];
                    source.read(buffer);
                    destination.write(buffer, 0, lenght);
                    //estraggo i bpm                **********************************
                    Process proc =  Runtime.getRuntime().exec(" java -jar src/trackanalyzer/TrackAnalyzer.jar " + copyFile.getCanonicalPath());
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                    String line = stdInput.readLine();
                    System.out.println(line);
                    double newSongBPM = Double.parseDouble(line.split("BPM: ")[1]);
                    System.out.println("BPM VALUE: "+ newSongBPM);
                    //creo il bson document e faccio l'upload sul database            *****************************
                    mongoDBConnector.uploadSong(correctName, buffer, newSongBPM);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //aggiungo il nome della canzone alla listView          *************************
                songs.add(correctName);
                songs.sort((a, b) -> a.compareTo(b));
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

    public MongoDBConnector getMongoDBConnector() {
        return mongoDBConnector;
    }

    public void setMongoDBConnector(MongoDBConnector mongoDBConnector) {
        this.mongoDBConnector = mongoDBConnector;
    }

}
