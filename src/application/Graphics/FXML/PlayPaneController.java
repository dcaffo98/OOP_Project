package application.Graphics.FXML;

import application.Graphics.item.scenes.GameScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class PlayPaneController {

    private Pane root;

    @FXML
    private BorderPane MainBorderPane;

    @FXML
    private Label LabelTest;

    @FXML
    private Button backButton;

    @FXML
    private Button playButton;

    public PlayPaneController(){

    }

    public void initialize () {

        hasParent();
        /* PROVARE A CREARE QUI UN LISTENER SUL MAINBORDERPANE CHE SI COLLEGA AL PARENT. SE NON FUNZIONA PROVARE AD AGGIUNGERE UN CONTROLLO SE IL PARENT ESISTE */
    }




    public void hasParent() {
        MainBorderPane.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                if (newValue != null) {
                    mainListener();
                    setRootPane((Pane) MainBorderPane.getParent());
                }
            }
        });
    }

    public void mainListener() {
        ((Pane)MainBorderPane.getParent()).prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {


                MainBorderPane.setPrefWidth(((Pane)MainBorderPane.getParent()).getPrefWidth() *  1);
                MainBorderPane.setPrefHeight(((Pane)MainBorderPane.getParent()).getPrefHeight() *  1);

                //.bind(((Pane)MainBorderPane.getParent()).prefWidthProperty());
                //MainBorderPane.prefHeightProperty().bind(((Pane) MainBorderPane.getParent()).prefHeightProperty());



            }
        });
    }

    public void setRootPane(Pane root) { this.root = root; }

    @FXML
    public void backButtonClicked(ActionEvent event) {
        this.root.getChildren().remove(MainBorderPane);
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
