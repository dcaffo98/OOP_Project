package application.Graphics.item.panes;

import application.Graphics.item.scenes.MainScene;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainPane extends AnchorPane {
    private Label title;
    public MainPane() {
        super();
        setBackground();
        setTitle(new Label("QUESTO E' IL TITOLO"));
        getChildren().add(title);


    }

    public void setBackground() {
        try {
            Image bgImage = new Image("resources/images/mainBackground.png");
            BackgroundImage background = new BackgroundImage(bgImage, null, null, BackgroundPosition.CENTER, null);
            setBackground(new Background(background));
        } catch(Exception e) {
            e.printStackTrace();
            setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        }

    }

    public Label getTitle() {
        return title;
    }

    public void setTitle(Label title) {
        this.title = title;
    }

    public void manageTitle() {
        title.setLayoutY(getHeight() * 0.1);
        title.setLayoutX(getWidth() * 0.2);
        title.setPrefHeight(getHeight() * 0.2);
        title.setPrefWidth(getWidth() * 0.6);
        title.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        title.setAlignment(Pos.CENTER);
    }

    public void bindScene (MainScene scene) {
        prefWidthProperty().bind(scene.widthProperty());
        prefHeightProperty().bind(scene.heightProperty());
        manageTitle();
    }
}
