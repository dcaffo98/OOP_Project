package application.Graphics.FXML;

import application.Graphics.item.ParentGetter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

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

    @FXML
    private BorderPane songsBorderPane;

    @FXML
    private Button backButton;

    @FXML
    private Button playSongButton;

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
        //Media media = new Media("file:/C:/PATH");
        File directory = new File("file:/C:/Users/david/Desktop/RythmUp");
        playSongButton.setText("Stop");
        String filename = directory.getPath() + "//" + songsListView.getSelectionModel().getSelectedItem().toString();
        AudioClip audioClip = new AudioClip(filename.replace("\\", "/"));
        playSongButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                audioClip.stop();
                playSongButton.setText("Play song");
            }
        });

        audioClip.play();
    }

}
