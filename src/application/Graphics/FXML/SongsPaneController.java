package application.Graphics.FXML;

import application.Graphics.item.ParentGetter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
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
                String filename = directory.getPath() + "//" + songsListView.getSelectionModel().getSelectedItem().toString();
                media = new Media(filename.replace("\\", "/"));
                if (mediaPlayer != null) {
                    mediaPlayer.dispose();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
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
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }
        root.getChildren().remove(songsBorderPane);
    }

}
