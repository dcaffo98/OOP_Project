package application.Graphics.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ScorePaneController {

    @FXML
    BorderPane scoreBorderPane;

    @FXML
    private Button backButton;

    @FXML
    private TableView scoreTable;

    @FXML
    private TableColumn playerNameCol;

    @FXML
    private TableColumn scoreCol;

    @FXML
    private TableColumn songCol;

    @FXML
    public void initialize() {}

    @FXML
    public void backButtonClicked(ActionEvent event) {((Pane) scoreBorderPane.getParent()).getChildren().remove(scoreBorderPane);}
}
