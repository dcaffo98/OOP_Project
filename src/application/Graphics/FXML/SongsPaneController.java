package application.Graphics.FXML;

import application.Graphics.item.ParentGetter;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongsPaneController {

    private Pane root;
    private ParentGetter parentGetter;
    private MediaPlayer mediaPlayer;
    private Media media;
    //da eliminare dopo integrazione database
    private final File directory = new File("file:/C:/Users/david/Desktop/RythmUp");

    @FXML
    private BorderPane songsBorderPane;

    @FXML
    private Button backButton;

    @FXML
    private HBox mediaBar;

    @FXML
    private Button playSongButton;

    @FXML
    private Button pauseSongButton;

    @FXML
    private Button uploadSongButton;

    @FXML
    private ListView songsListView;

    public SongsPaneController() {
    }

    public void initialize() {
        parentGetter = new ParentGetter(songsBorderPane, root) {
            @Override
            public void parentGotten() {
                setRootPane((Pane) getParent());
            }
        };

        populateListView();

        songsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                playSongButton.setDisable(false);
                playSongButton.setOpacity(1);
                pauseSongButton.setDisable(false);
                pauseSongButton.setOpacity(1);
                String filename = directory.getPath() + "//" + songsListView.getSelectionModel().getSelectedItem().toString();
                if (mediaPlayer != null && mediaPlayer.getCurrentTime().greaterThan(new Duration(0))) {
                    //mediaPlayer.dispose();

                }
                media = new Media(filename.replace("\\", "/"));
                //mediaPlayer = new MediaPlayer(media);
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer(media);
                }
            }
        });
    }

    public void setRootPane(Pane root) {
        this.root = root;
    }

    public void populateListView() {
        File directory = new File("C://Users/david/Desktop/RythmUp");
        List<String> songsList = new ArrayList<String>(Arrays.asList(directory.list()));
        for (String song : songsList) {
            System.out.println(song);
            songsListView.getItems().add(song);
        }
    }

    @FXML
    public void backButtonCliked(ActionEvent event) {
        root.getChildren().remove(songsBorderPane);
    }

    @FXML
    public void playSongButtonClicked(ActionEvent event) {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.READY) {
            if (mediaPlayer.getCurrentTime().greaterThan(new Duration(0))) {
                mediaPlayer.dispose();
            }
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }

    @FXML
    public void pauseSongButtonClicked(ActionEvent event) {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING || media != mediaPlayer.getMedia()) {
            mediaPlayer.pause();
        }
    }

}
