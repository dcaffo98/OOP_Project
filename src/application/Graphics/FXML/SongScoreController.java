package application.Graphics.FXML;

import application.Graphics.item.MongoDBConnector;
import application.Graphics.item.scenes.GameScene;
import com.mongodb.MongoClient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class SongScoreController {

    private final String SONGS_PATH = "src/trackanalyzer/songs/";
    private ObservableList<String> songs;
    private Media selectedSong;
    private MongoDBConnector mongoDBConnector;

    @FXML
    private BorderPane songScoreBorderPane;

    @FXML
    private ComboBox<String> songsComboBox;

    @FXML
    private Button backButton;


    @FXML
    public void initialize () {}

    @FXML
    public void backButtonClicked(ActionEvent event) {
        ((Pane) songScoreBorderPane.getParent()).getChildren().remove(songScoreBorderPane);
    }

    @FXML
    public void songsComboBoxSelectionChange(ActionEvent event) {
        String selectedSong = songsComboBox.getSelectionModel().getSelectedItem();
        ArrayList<Pair<String,Integer>> scoreTable = mongoDBConnector.downloadSongScore(selectedSong);
        int posizione = 1;
        for (Pair<String,Integer> score: scoreTable) {

            System.out.println("Name:  "+score.getKey()+" Score:  "+score.getValue());
            posizione++;
        }
    }

    public MongoDBConnector getMongoDBConnector() {
        return mongoDBConnector;
    }

    public void setMongoDBConnector(MongoDBConnector mongoDBConnector) {
        this.mongoDBConnector = mongoDBConnector;
    }

    public void setComboBoxItems() {
        songs = FXCollections.observableArrayList(mongoDBConnector.populateSongList());
        songsComboBox.getItems().setAll(songs);
    }
}
