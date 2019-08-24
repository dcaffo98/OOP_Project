package application.Graphics.FXML;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class PlayPaneController {

    @FXML
    BorderPane MainBorderPane;


    @FXML
    Label LabelTest;

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


}
