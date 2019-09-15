package application.Graphics.FXML;

import application.Graphics.item.gameObjects.FrameHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PausePaneController {

    private Pane parentPane;
    private FrameHandler frameHandler;

    @FXML
    private StackPane pausePane;

    @FXML
    private Button resumeButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button backToMenuButton;

    @FXML
    public void initialize() {
        pausePane.requestFocus();
    }

    @FXML
    public void resumeButtonClicked(ActionEvent event) {
        parentPane.getChildren().remove(pausePane);
        //frameHandler.onResumeRequest();
    }

    public Pane getParentPane() {
        return parentPane;
    }

    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    public FrameHandler getFrameHandler() {
        return frameHandler;
    }

    public void setFrameHandler(FrameHandler frameHandler) {
        this.frameHandler = frameHandler;
    }

    public void setLayout(Pane parentPane) {
        setParentPane(parentPane);
        pausePane.setLayoutX((parentPane.getPrefWidth() + pausePane.getWidth()) / 2);
        pausePane.setLayoutY((parentPane.getPrefHeight() + pausePane.getHeight()) / 2);
        //pausePane.layoutXProperty().bind(parentPane.layoutXProperty().add((parentPane.getPrefWidth() + pausePane.getWidth()) / 2));
        //pausePane.layoutYProperty().bind(parentPane.layoutYProperty().add((parentPane.getPrefHeight() + pausePane.getHeight()) / 2));
    }
}
