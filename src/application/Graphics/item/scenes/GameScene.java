package application.Graphics.item.scenes;

import application.Graphics.item.MongoDBConnector;
import application.Graphics.item.gameObjects.FrameHandler;
import application.Graphics.item.gameObjects.PlayerBar;
import application.Graphics.item.gameObjects.Note;
import application.Graphics.item.panes.GameTopPane;
import application.Graphics.item.stages.MainStage;
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
    private double bpm;

    public GameScene(Parent root, double width, double height, Media selectedSong, double bpm, MongoDBConnector mongoDBConnector, MainStage mainStage) {
        super(root, width, height);
        gamePane = (AnchorPane) root;
        gameTopPane = new GameTopPane();
        this.selectedSong = selectedSong;
        this.bpm = bpm;
        this.mediaPlayer = new MediaPlayer(this.selectedSong);
        playerBar = new PlayerBar("resources/images/paddleRed.png");
        gamePane.getChildren().addAll(gameTopPane,playerBar);
        this.timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.frameHandler = new FrameHandler(gamePane,gameTopPane,playerBar,timeline,mediaPlayer,this.bpm, mongoDBConnector, mainStage);
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
