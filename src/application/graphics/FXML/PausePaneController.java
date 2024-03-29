package application.graphics.FXML;

import application.graphics.item.gameObjects.FrameHandler;
import application.graphics.item.stages.MainStage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PausePaneController {

    private Pane parentPane;
    private FrameHandler frameHandler;
    private MainStage mainStage;

    @FXML
    private AnchorPane pausePane;

    @FXML
    private Button resumeButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button backToMenuButton;

    @FXML
    public void initialize() {
    }

    /*
    metodo che fa riprendere il gioco dal punto in cui era stato stoppato
     */
    @FXML
    public void resumeButtonClicked(ActionEvent event) {
        resume();
    }

    /*
    metodo che fa ripartire il gioco dall'inizio
    */
    @FXML
    public void restartButtonClicked(ActionEvent event) {
        frameHandler.restart();
        parentPane.getChildren().remove(pausePane);
    }

    /*
    esce dal gameplay e ritorna al menu principale
     */
    @FXML
    public void backToMenuButtonClicked(ActionEvent event) {
        ObservableList<Node> childrenList = ((Pane) mainStage.getScene().getRoot()).getChildren();
        for (Node n: childrenList) {
            if (n instanceof BorderPane) {
                ((Pane) mainStage.getScene().getRoot()).getChildren().remove(n);
                break;
            }
        }
        mainStage.show();
        ((Stage) parentPane.getScene().getWindow()).close();
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

    public MainStage getMainStage() {
        return mainStage;
    }

    public void setMainStage(MainStage mainStage) {
        this.mainStage = mainStage;
    }

    /*
    metodo chiamato dal loader dell'FXML per inizializzare alcuni attributi della classe
     */
    public void setData(Pane parentPane, FrameHandler frameHandler, MainStage mainStage) {
        this.parentPane = parentPane;
        this.frameHandler = frameHandler;
        this.mainStage = mainStage;
        pausePane.requestFocus();
    }

    /*
    metodo chiamato dal loader dell'FXML per inizializzare il layout del PausePane
     */
    public void setLayout() {
        ((Stage) parentPane.getScene().getWindow()).setResizable(false);
        pausePane.setLayoutX((parentPane.getWidth() - pausePane.getPrefWidth()) / 2);
        pausePane.setLayoutY((parentPane.getHeight() - pausePane.getPrefHeight()) / 2);
    }

    /*
    metodo per far ripartire il gameplay
     */
    public void resume() {
        parentPane.getChildren().remove(pausePane);
        ((Stage) parentPane.getScene().getWindow()).setResizable(true);
        frameHandler.onResumeRequest();
    }

}
