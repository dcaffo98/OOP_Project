package application.Graphics.FXML;

import application.Graphics.item.HighScoreTableRow;
import application.Graphics.item.MongoDBConnector;
import application.Graphics.item.SongScoreTableRow;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;

public class HighScorePaneController {

    private MongoDBConnector mongoDBConnector;

    @FXML
    BorderPane highScoreBorderPane;

    @FXML
    private Button backButton;

    @FXML
    private TableView highScoreTable;

    @FXML
    private TableColumn positionCol;

    @FXML
    private TableColumn usernameCol;

    @FXML
    private TableColumn scoreCol;

    @FXML
    private TableColumn songCol;

    @FXML
    public void initialize() {}

    @FXML
    public void backButtonClicked(ActionEvent event) {((Pane) highScoreBorderPane.getParent()).getChildren().remove(highScoreBorderPane);}

    public MongoDBConnector getMongoDBConnector() {
        return mongoDBConnector;
    }

    public void setMongoDBConnector(MongoDBConnector mongoDBConnector) {
        this.mongoDBConnector = mongoDBConnector;
    }

    public void setTableViewItems() {

        ArrayList<HighScoreTableRow> highScores = mongoDBConnector.downloadHighScore();
        highScores.sort((a, b) -> ((Integer)a.getScore()).compareTo(((Integer) b.getScore())));
        Collections.reverse(highScores);
        int index = 1;
        for (HighScoreTableRow row:highScores) {
            row.setIndex(index);
            index++;
        }

        positionCol.setCellValueFactory(new PropertyValueFactory<SongScoreTableRow, Integer>("index"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<SongScoreTableRow, String>("name"));
        songCol.setCellValueFactory(new PropertyValueFactory<SongScoreTableRow, String>("songName"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<SongScoreTableRow, Integer>("score"));

        highScoreTable.setItems(FXCollections.observableArrayList(highScores));
    }
}
