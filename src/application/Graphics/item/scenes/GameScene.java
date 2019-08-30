package application.Graphics.item.scenes;

import application.Graphics.item.gameObjects.FrameHandler;
import application.Graphics.item.gameObjects.PlayerBar;
import application.Graphics.item.gameObjects.Note;
import application.Graphics.item.panes.GameTopPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameScene extends Scene {

    private static final double KEYBOARD_MOVEMENT_DELTA = 5;

    private AnchorPane gamePane;
    private GameTopPane gameTopPane;
    private PlayerBar playerBar;
    private FrameHandler frameHandler;
    private Note note;

    public GameScene(Parent root, double width, double height) {
        super(root, width, height);
        gamePane = (AnchorPane) root;
        gameTopPane = new GameTopPane();
        playerBar = new PlayerBar("resources/images/paddleRed.png");

        gamePane.getChildren().addAll(gameTopPane,playerBar);


        Timeline timeline = new Timeline();
        this.frameHandler = new FrameHandler(gamePane,gameTopPane,playerBar,timeline);
        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.getKeyFrames()
                .add(new KeyFrame(Duration.millis(16), frameHandler));
        timeline.play();
        setKeyListener();
    }




    public void setKeyListener() {
        setOnKeyPressed((KeyEvent key) -> {
            KeyCode code = key.getCode();
            switch (code) {
                case RIGHT:
                case LEFT:
                    frameHandler.setCode(code);
                    break;
            }
        });

        setOnKeyReleased((KeyEvent key) -> {
            KeyCode code = key.getCode();
            if (frameHandler.getCode() == code) {
                frameHandler.setCode(null);
            }
        });
    }


}
