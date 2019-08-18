package application.Graphics;

import application.Graphics.item.MainSubScene;
import application.Graphics.item.myButton;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Viewer {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private DoubleProperty sceneWidth;
    private  DoubleProperty sceneHeight;
    private double subSceneWidth;
    private double subSceneHeight;
    private double subSceneLayoutX;
    private double subSceneLayoutY;
    private boolean subSceneShowed;

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private TilePane menu;
    private Label title;
    private myButton play;
    private myButton score;
    private myButton songs;
    private myButton exit;
    private List<myButton> menuButtons = new ArrayList<myButton>();
    private MainSubScene subScene;

    public Viewer() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        menu = new TilePane();
        title = new Label("THIS IS THE TITLE");
        mainStage.setScene(mainScene);
        mainStage.setTitle("OOP_Exam");
        mainStage.setMinHeight(HEIGHT);
        mainStage.setMinWidth(WIDTH);
        mainScene.getStylesheets().add("resources/style/stylesheet.css");
        sceneWidth = new SimpleDoubleProperty();
        sceneWidth.bind(mainPane.widthProperty());
        sceneHeight = new SimpleDoubleProperty();
        sceneHeight.bind(mainPane.heightProperty());
        subSceneWidth = sceneWidth.get() * 0.6;
        subSceneHeight = sceneHeight.get() * 0.5;
        subSceneLayoutX = sceneWidth.get() * 0.2;
        subSceneLayoutY = sceneHeight.get() * 0.4;
        subScene = new MainSubScene();
        subSceneShowed = false;
        setBackground();
        setTitle();
        createPlayButton();
        createScoreButton();
        createSongsButton();
        createExitButton();
        setMenuButtons();
        setMenu();
        mainPane.requestFocus();
        setMainSceneChangeListener();
        manageSubScene();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setBackground() {
        try {
            Image bgImage = new Image("resources/images/mainBackground.png");
            BackgroundImage background = new BackgroundImage(bgImage, null, null, BackgroundPosition.CENTER, null);
            mainPane.setBackground(new Background(background));
        } catch(Exception e) {
            e.printStackTrace();
            mainPane.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        }

    }

    public void createPlayButton() {
        play = new myButton("Play");
        menuButtons.add(play);
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                subScene = createSubScene();
            }
        });
    }

    public void createScoreButton() {
        score = new myButton("Score");
        menuButtons.add(score);
        score.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
    }

    public void createSongsButton() {
        songs = new myButton("Songs");
        menuButtons.add(songs);
    }

    public void createExitButton() {
        exit = new myButton("Exit");
        menuButtons.add(exit);
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    public void setMenuButtons() {
        for (myButton button : menuButtons) {
            button.setMaxWidth(Double.MAX_VALUE);
        }
    }

    public void setMenu() {
        menu.setLayoutX(mainPane.getWidth() * 0.4);
        menu.setLayoutY(mainPane.getHeight() * 0.4);
        menu.setPrefWidth(mainPane.getWidth() * 0.2);
        menu.setPrefHeight(mainPane.getHeight() * 0.5);
        menu.setPrefTileWidth(mainPane.getWidth() * 0.2);
        menu.setPrefTileHeight(mainPane.getHeight() * 0.5 / menuButtons.size());
        menu.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        for (myButton button : menuButtons)
            menu.getChildren().add(button);
        mainPane.getChildren().add(menu);
    }

    //ridimensionare dinamicamente elementi della scena principale
    public void setMainSceneChangeListener() {
        sceneWidth.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double newWidth = (double) newValue;
                title.setLayoutX(newWidth * 0.2);
                title.setPrefWidth(newWidth * 0.6);
                menu.setPrefWidth(newWidth * 0.2);
                menu.setPrefTileWidth(newWidth * 0.2);
                menu.setLayoutX(newWidth * 0.4);
                subSceneLayoutX = newWidth * 0.2;
                subSceneWidth = newWidth * 0.6;
                if (subSceneShowed) {
                    subScene.setLayoutX(subSceneLayoutX);
                    subScene.setWidth(subSceneWidth);
                }
            }
        });

        sceneHeight.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double newHeight = (double) newValue;
                title.setLayoutY(newHeight * 0.1);
                title.setPrefHeight(newHeight * 0.2);
                menu.setPrefHeight(newHeight * 0.5);
                menu.setPrefTileHeight(sceneHeight.get() * 0.5 / menuButtons.size());
                menu.setLayoutY(newHeight * 0.4);
                subSceneLayoutY = newHeight * 0.4;
                subSceneHeight = newHeight * 0.5;
                if (subSceneShowed) {
                    subScene.setLayoutY(subSceneLayoutY);
                    subScene.setHeight(subSceneHeight);
                }
            }
        });
    }

    public void setTitle() {
        title.setLayoutY(mainPane.getHeight() * 0.1);
        title.setLayoutX(mainPane.getWidth() * 0.2);
        title.setPrefHeight(mainPane.getHeight() * 0.2);
        title.setPrefWidth(mainPane.getWidth() * 0.6);
        title.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        title.setAlignment(Pos.CENTER);
        title.setFont(new Font("Verdana", 40));
        mainPane.getChildren().add(title);
    }

    public MainSubScene createSubScene() {
        MainSubScene subScene = new MainSubScene(new AnchorPane(), subSceneWidth, subSceneHeight);
        mainPane.getChildren().add(subScene);
        subSceneShowed = true;
        subScene.setLayoutX(subSceneLayoutX);
        subScene.setLayoutY(subSceneLayoutY);
        return subScene;
    }

    public void manageSubScene() {
        subScene.toShow.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue.booleanValue() == true) {
                    subSceneShowed = true;
                    System.out.println("subSceneShowed!");
                } else {
                    subSceneShowed = false;
                    mainPane.getChildren().remove(subScene);
                    System.out.println("subSceneNotShowed!");
                }
            }
        });
    }
}
