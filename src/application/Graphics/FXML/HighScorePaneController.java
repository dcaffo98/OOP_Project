package application.Graphics.FXML;

import application.Graphics.item.MongoDBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class HighScorePaneController {

    private MongoDBConnector mongoDBConnector;

    @FXML
    BorderPane highScoreBorderPane;

    @FXML
    private Button backButton;

    @FXML
    private TableView highScoreTable;

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

    public void doStuffWithDB() {mongoDBConnector.downloadHighScore();}
}
