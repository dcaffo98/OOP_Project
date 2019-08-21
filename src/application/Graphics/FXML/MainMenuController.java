package application.Graphics.FXML;

import application.Graphics.item.panes.MainPane;
import application.Graphics.item.panes.SubScenePane;
import application.Graphics.item.scenes.MainScene;
import application.Graphics.item.scenes.MainSubScene;
import application.Graphics.item.stages.MainStage;
import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MainMenuController {

    @FXML
    private BorderPane MainMenuBorderPanel;

    @FXML
    private ImageView Title;

    @FXML
    private VBox MainBox;

    @FXML
    private Button playButton;

    @FXML
    private Button scoreButton;

    @FXML
    private Button songsButton;

    @FXML
    private Button exitButton;

    private List<Button> buttons;
    private SubScenePane subScenePane;
    private MainSubScene playSubScene;

    public MainMenuController(){
    }


    public void initialize() {

        buttons = new ArrayList<Button>();
        buttons.add(playButton);
        buttons.add(scoreButton);
        buttons.add(songsButton);
        buttons.add(exitButton);

        //creo un'istanza di SubScenePane e MainSubScene, NON vengono visualizzati
        subScenePane = new SubScenePane(MainMenuBorderPanel);
        playSubScene = new MainSubScene(subScenePane);
        //listener che gestisce le sottoscene (work in progress)
        manageSubScene();

        MainBox.prefHeightProperty().bind(MainMenuBorderPanel.heightProperty());
        MainBox.prefWidthProperty().bind(MainMenuBorderPanel.widthProperty());
        //Title.fitWidthProperty().bind(MainMenuBorderPanel.widthProperty());
        Title.fitHeightProperty().bind(MainMenuBorderPanel.heightProperty());

        MainMenuBorderPanel.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Title.setFitWidth(MainMenuBorderPanel.getWidth() * 0.3);
                // 4 bottoni, 3 spacing  15 10 15 10 15 10 15
                //MainBox.setSpacing(MainBox.getHeight() * 0.1);
                MainBox.setPadding(new Insets(MainBox.getHeight() * 0.05, MainBox.getWidth() * 0.05, MainBox.getHeight() * 0.05, MainBox.getWidth() * 0.05));
                for (Button b: buttons) {

                    b.setPrefWidth(MainBox.getWidth() * 0.2);
                    //b.setPrefHeight(MainBox.getHeight() * 0.15);
                }

            }
        });

        MainMenuBorderPanel.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //Title.setFitHeight(MainMenuBorderPanel.getHeight() * 0.3);
                // 4 bottoni, 3 spacing  15 10 15 10 15 10 15
                MainBox.setSpacing(MainBox.getHeight() * 0.1);
                for (Button b: buttons) {
                    b.setPrefHeight(MainBox.getHeight() * 0.15);
                }

            }
        });

    }


    @FXML
    public void playOnClick () {
        System.out.println("Clikkato!!");
        MainMenuBorderPanel.getChildren().remove(MainBox);
        playSubScene.setIsShowed(true);
        MainMenuBorderPanel.getChildren().add(subScenePane);
        System.out.println(playSubScene.isShowedProperty().get());
    }


    public void manageSubScene() {
            playSubScene.isShowedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if ( !newValue.booleanValue()) {
                        MainMenuBorderPanel.getChildren().remove(subScenePane);
                        MainMenuBorderPanel.getChildren().add(MainBox);
                        System.out.println(playSubScene.isShowedProperty().get());
                    }
                }
            });
    }
}
