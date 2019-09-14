package application.Graphics.FXML;

import application.Graphics.item.MongoDBConnector;
import application.Graphics.item.stages.MainStage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ResultPaneController {

    private String username;
    private MongoDBConnector mongoDBConnector;
    private MainStage mainStage;

    @FXML
    private BorderPane resultBorderPane;

    @FXML
    private Label songNameLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label hitNotesLabel;

    @FXML
    private Label missedNotesLabel;

    @FXML
    private Label maxComboLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button saveButton;

    @FXML
    public void initialize() {
    }

    @FXML
    public void exitButtonClicked(ActionEvent event) throws Exception {
        ObservableList<Node> childrenList = ((Pane) mainStage.getScene().getRoot()).getChildren();
        for (Node n: childrenList) {
            if (n instanceof BorderPane) {
                ((Pane) mainStage.getScene().getRoot()).getChildren().remove(n);
                break;
            }
        }

        mainStage.show();
        ((Stage) exitButton.getScene().getWindow()).close();

    }

    @FXML
    public void saveButtonClicked(ActionEvent event) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText("Insert a username");
        ((Stage) textInputDialog.getDialogPane().getScene().getWindow()).initStyle(StageStyle.UNDECORATED);
        textInputDialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent dialogEvent) {
                username = textInputDialog.getEditor().getText();
                if (username.isBlank()) {
                    System.out.println("Empty username or null username");
                    dialogEvent.consume();
                }
                else {
                    System.out.println(username);
                }
            }
        });

        textInputDialog.showAndWait();
        System.out.println("YOUR USERNAME: " + username);
    }


    public void setData(int maxCombo, String songName, int score, int missedNotes, int hitNotes, MongoDBConnector mongoDBConnector, MainStage mainStage) {
        scoreLabel.setText(((Integer) score).toString());
        maxComboLabel.setText("Max combo: " + ((Integer) maxCombo).toString());
        hitNotesLabel.setText("Hit notes: " + ((Integer) hitNotes).toString());
        missedNotesLabel.setText("Missed notes: " + ((Integer) missedNotes).toString());
        songNameLabel.setText(songName.split("/")[songName.split("/").length - 1]);
        setMongoDBConnector(mongoDBConnector);
        this.mainStage = mainStage;

    }

    public MongoDBConnector getMongoDBConnector() {
        return mongoDBConnector;
    }

    public void setMongoDBConnector(MongoDBConnector mongoDBConnector) {
        this.mongoDBConnector = mongoDBConnector;
    }
}
