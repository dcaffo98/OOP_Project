package application.Graphics.FXML;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

import static java.lang.Math.abs;

public class SongsPaneController {

    private MediaPlayer mediaPlayer;
    private Media media;
    //da eliminare dopo integrazione database
    private final String songsPath = "src/trackanalyzer/songs";
    private ObservableList<String> songs;

    @FXML
    private BorderPane songsBorderPane;

    @FXML
    private Button backButton;

    @FXML
    private Button uploadSongButton;

    @FXML
    private ListView<String> songsListView;

    @FXML
    public void initialize() {
        //populateListView();
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

    public void populateListView() {
        LinkedHashSet<String> songListWithoutDuplicate = new LinkedHashSet<>(Arrays.asList(new File(songsPath).list()));
        for (String song : songListWithoutDuplicate) {
            songsListView.getItems().add(song);
        }
    }

    @FXML
    public void uploadSongButtonClicked(ActionEvent event) {
        List<File> fileList = new FileChooser().showOpenMultipleDialog(songsBorderPane.getScene().getWindow());
        if (fileList != null) {
            for (File file : fileList) {
                //String filename = new File("C://Users/david/Desktop/RythmUp").getPath() + "\\" + file.getName();
                Path filenamex = Paths.get(songsPath + "/" + file.getName());
                File copyFile = new File(filenamex.toUri());
                try {
                    copyFile.createNewFile();
                    FileInputStream source = new FileInputStream(file);
                    FileOutputStream destination = new FileOutputStream(copyFile);
                    byte[] buffer = new byte[1024];
                    int lenght;
                    while ((lenght = source.read(buffer)) > 0) {
                        destination.write(buffer, 0, lenght);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (songsListView.getItems().contains(copyFile.getName())) {
                    System.out.println("This song is already available" + copyFile.getName());
                    continue;
                } else {
                    //songsListView.getItems().add(copyFile.getName());
                    songs.add(copyFile.getName());
                    songs.sort((a, b) -> a.compareTo(b));
                    for (String song : songs)
                        System.out.println(song);
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
        ((Pane) songsBorderPane.getParent()).getChildren().remove(songsBorderPane);
    }

}
