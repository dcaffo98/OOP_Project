package application.Graphics.FXML;

import application.Graphics.item.scenes.ScoreScene;
import application.Graphics.item.stages.MainStage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.event.ActionEvent;

public class ResultPaneController {

    private ScoreScene scoreScene;

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
    public void exitButtonClicked(javafx.event.ActionEvent event) {
        //MainStage mainStage = new MainStage(800, 600, "RythmUp");
        //mainStage.show();
        ((Stage) exitButton.getScene().getWindow()).close();

    }


    public void setData(int maxCombo, String songName, int score, int missedNotes, int hitNotes) {
        scoreLabel.setText(((Integer) score).toString());
        maxComboLabel.setText("Max combo: " + ((Integer) maxCombo).toString());
        hitNotesLabel.setText("Hit notes: " + ((Integer) hitNotes).toString());
        missedNotesLabel.setText("Missed notes: " + ((Integer) missedNotes).toString());
        songNameLabel.setText(songName.split("/")[songName.split("/").length - 1]);
    }

}