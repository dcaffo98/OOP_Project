package application.graphics.FXML;

import application.graphics.item.MongoDBConnector;
import application.graphics.item.SongScoreTableRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;

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
    private TableView ranking;

    @FXML
    private TableColumn position;

    @FXML
    private TableColumn username;

    @FXML
    private TableColumn score;


    @FXML
    public void initialize () {}

    @FXML
    public void backButtonClicked(ActionEvent event) {
        ((Pane) songScoreBorderPane.getParent()).getChildren().remove(songScoreBorderPane);
    }

    /*
    gestisce la selezione degli elementi dalla ComboBox;
    viene interrogato il db per scaricare e visualizzare la classifica dei punteggi della canzone attualmente selezionata
     */
    @FXML
    public void songsComboBoxSelectionChange(ActionEvent event) {
        String selectedSong = songsComboBox.getSelectionModel().getSelectedItem();
        ArrayList<Pair<String,Integer>> scoreTable = mongoDBConnector.downloadSongScore(selectedSong);
        scoreTable.sort((a,b) -> a.getValue().compareTo(b.getValue()));
        Collections.reverse(scoreTable);
        ArrayList<SongScoreTableRow> tableRows = new ArrayList<SongScoreTableRow>();
        int index = 1;
            for (Pair<String, Integer> score : scoreTable) {
                tableRows.add(new SongScoreTableRow(index,score.getKey(),score.getValue()));
                index++;
            }

        position.setCellValueFactory(new PropertyValueFactory<SongScoreTableRow, Integer>("index"));
        username.setCellValueFactory(new PropertyValueFactory<SongScoreTableRow, String>("name"));
        score.setCellValueFactory(new PropertyValueFactory<SongScoreTableRow, Integer>("score"));

        ranking.setItems(FXCollections.observableArrayList(tableRows));
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
