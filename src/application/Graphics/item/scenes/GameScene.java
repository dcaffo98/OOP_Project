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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameScene extends Scene {

    private static final double KEYBOARD_MOVEMENT_DELTA = 5;

    private AnchorPane gamePane;
    private GameTopPane gameTopPane;
    private PlayerBar playerBar;
    private Timeline timeline;
    private FrameHandler frameHandler;
    private Note note;
    private Media selectedSong;
    private MediaPlayer mediaPlayer;

    public GameScene(Parent root, double width, double height, Media selectedSong) {
        super(root, width, height);
        gamePane = (AnchorPane) root;
        gameTopPane = new GameTopPane();
        this.selectedSong = selectedSong;
        this.mediaPlayer = new MediaPlayer(this.selectedSong);
        playerBar = new PlayerBar("resources/images/paddleRed.png");
        gamePane.getChildren().addAll(gameTopPane,playerBar);
        this.timeline = new Timeline();
        this.frameHandler = new FrameHandler(gamePane,gameTopPane,playerBar,timeline,mediaPlayer);
        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.getKeyFrames()
                .add(new KeyFrame(Duration.millis(16), frameHandler));
        mediaPlayer.play();
        timeline.play();
        setKeyListener();
    }




    public void setKeyListener() {
        setOnKeyPressed((KeyEvent key) -> {
            KeyCode code = key.getCode();
            switch (code) {
                case RIGHT:
                case LEFT:
                case ESCAPE:
                    frameHandler.setCode(code);
                    break;
                case ENTER:
                    timeline.play();
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
