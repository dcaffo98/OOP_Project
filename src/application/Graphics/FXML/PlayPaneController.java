package application.Graphics.FXML;

import application.Graphics.item.ParentGetter;
import application.Graphics.item.scenes.GameScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class PlayPaneController {

    private Pane root;
    private ParentGetter parentGetter;

    @FXML
    private BorderPane playBorderPane;

    @FXML
    private Label labelTest;

    @FXML
    private Button backButton;

    @FXML
    private Button playButton;

    public PlayPaneController(){

    }

    public void initialize () {
        parentGetter = new ParentGetter(playBorderPane, root) {
            @Override
            public void parentGotten() {
                root = (Pane) getParent();
            }
        };
    }


    //INUTILE????
    public void mainListener() {
        ((Pane) playBorderPane.getParent()).prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                playBorderPane.setPrefWidth(((Pane) playBorderPane.getParent()).getPrefWidth() *  1);
                playBorderPane.setPrefHeight(((Pane) playBorderPane.getParent()).getPrefHeight() *  1);
            }
        });
    }


    @FXML
    public void backButtonClicked(ActionEvent event) {
        this.root.getChildren().remove(playBorderPane);
    }

    @FXML
    public void playButtonClicked(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).close();
        System.out.println("Let's go!");
        Stage playStage = new Stage();
        playStage.setMinWidth(1200);
        playStage.setMinHeight(800);
        playStage.setScene(new GameScene(new AnchorPane(), playStage.getMinWidth(), playStage.getMinHeight()));
        playStage.setTitle("RythmUp");
        playStage.show();
    }


}
