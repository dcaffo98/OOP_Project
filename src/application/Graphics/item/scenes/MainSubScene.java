package application.Graphics.item.scenes;

import application.Graphics.item.myButton;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class MainSubScene extends SubScene {

    private AnchorPane sceneRoot;
    private myButton backButton;
    public BooleanProperty toShow = new SimpleBooleanProperty();

    public MainSubScene(){
        super(new AnchorPane(), 0, 0);
        toShow.set(false);
    }

    public MainSubScene(Parent root, double width, double height) {
        super(root, width, height);
        sceneRoot = (AnchorPane) this.getRoot();
        sceneRoot.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        toShow.set(true);
        backButton = new myButton("Back");
        sceneRoot.getChildren().add(backButton);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toShow.set(false);
            }
        });
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);
    }

}
