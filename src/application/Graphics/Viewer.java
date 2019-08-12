package application.Graphics;

import application.Graphics.item.myButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Viewer {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private TilePane menu;

    private List<myButton> menuButtons = new ArrayList<myButton>();

    public Viewer() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        menu = new TilePane();
        mainStage.setScene(mainScene);
        mainStage.setTitle("OOP_Exam");
        mainStage.setMinHeight(HEIGHT);
        mainStage.setMinWidth(WIDTH);
        setBackground();
        createButton("Play");
        createButton("Score");
        createButton("Songs");
        createButton("Exit");
        setMenu();
        setMenuChangeListener();
        mainPane.requestFocus();
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

    public void createButton(String text) {
        myButton button = new myButton(text);
        button.setMaxWidth(Double.MAX_VALUE);
        menuButtons.add(button);
    }

    public void setMenu() {
        menu.setLayoutX(mainPane.getWidth() * 0.4);
        menu.setLayoutY(mainPane.getHeight() * 0.2);
        menu.setPrefWidth(mainPane.getWidth() * 0.2);
        menu.setPrefHeight(mainPane.getHeight() * 0.6);
        menu.setPrefTileWidth(mainPane.getWidth() * 0.2);
        menu.setPrefTileHeight(mainPane.getHeight() * 0.6 / menuButtons.size());
        menu.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        for (myButton button : menuButtons)
            menu.getChildren().add(button);
        mainPane.getChildren().add(menu);
    }

    //ridimensionare dinamicamente i pulsanti del menu principale
    public void setMenuChangeListener() {
        mainPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double newWidth = (double) newValue;
                menu.setPrefWidth(newWidth * 0.2);
                menu.setPrefTileWidth(newWidth * 0.2);
                menu.setLayoutX(newWidth * 0.4);
                System.out.println("Pane width: " + menu.getWidth());
                for (myButton button : menuButtons)
                    System.out.println("Button width: " + button.getWidth());
            }
        });

        mainPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double newHeight = (double) newValue;
                menu.setPrefHeight(newHeight * 0.6);
                menu.setPrefTileHeight(mainPane.getHeight() * 0.6 / menuButtons.size());
                menu.setLayoutY(newHeight * 0.2);
            }
        });

    }
}
