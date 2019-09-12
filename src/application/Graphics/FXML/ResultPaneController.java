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

        resultBorderPane.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observableValue, Scene oldScene, Scene newScene) {
                if (newScene != null) {
                    scoreScene = (ScoreScene) resultBorderPane.getScene();
                    scoreLabel.setText(((Integer) scoreScene.getScore()).toString());
                    maxComboLabel.setText("Max combo: " + ((Integer) scoreScene.getMaxCombo()).toString());
                    hitNotesLabel.setText("Hit notes: " + ((Integer) scoreScene.getHitNotes()).toString());
                    missedNotesLabel.setText("Missed notes: " + ((Integer) scoreScene.getMissedNotes()).toString());
                    songNameLabel.setText(scoreScene.getSongName());
                }
            }
        });
    }

    @FXML
    public void exitButtonClicked(javafx.event.ActionEvent event) {
        //MainStage mainStage = new MainStage(800, 600, "RythmUp");
        //mainStage.show();
        ((Stage) exitButton.getScene().getWindow()).close();

    }
}
