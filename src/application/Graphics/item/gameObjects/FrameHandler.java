package application.Graphics.item.gameObjects;

import application.Graphics.item.panes.GameTopPane;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class FrameHandler implements EventHandler<ActionEvent> {

    private KeyCode code;
    private GameTopPane gameTopPane;
    private PlayerBar player;
    private Timeline timeline;
    private ArrayList<Note> notes;
    private AnchorPane gamePane;
    private MediaPlayer mediaPlayer;
    private int score;
    private int frameCounter;
    private double bpm;
    private double frameBeat;
    private double lastNoteTime;
    private double endOfGenerationTime;

    

    public FrameHandler(AnchorPane gamePane, GameTopPane gameTopPane, PlayerBar player, Timeline timeline, MediaPlayer mediaPlayer, double bpm) {

        this.gamePane = gamePane;
        this.gameTopPane = gameTopPane;
        this.player = player;
        this.timeline = timeline;
        this.score = 0;
        this.frameCounter = 0;
        this.notes = new ArrayList<Note>();
        this.bpm = bpm;
        this.mediaPlayer = mediaPlayer;
        this.frameBeat = (1.0 / (bpm/60.0)) / 0.016; //formula che indica quanti frame devono passare per aggiungere una nota.
        this.lastNoteTime = (frameBeat * 8) * 16; //millisecondi necessari all'ultima nota per arrivare alla fine dello schermo
        this.endOfGenerationTime = mediaPlayer.getMedia().getDuration().toMillis() - lastNoteTime;

    }

    public KeyCode getCode() {
        return code;
    }

    public void setCode(KeyCode code) {
        this.code = code;
    }

    @Override
    public void handle(ActionEvent event) {
        
        // move player, if key is pressed
        gamePane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        frameCounter++;
        if(mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getMedia().getDuration())) {

            timeline.stop();


        }




        if (code != null) {
            switch (code) {
                case RIGHT:
                    player.moveRight();
                    break;
                case LEFT:
                    player.moveLeft();
                    break;
                case ESCAPE:
                    timeline.pause();
                    mediaPlayer.pause();
                    break;
            }
        }
        if ((frameCounter > this.frameBeat) && (mediaPlayer.getCurrentTime().toMillis() < endOfGenerationTime)) {
            addNote();
            frameCounter = 0;
        }
        if (notes.size() > 0) {
            update();
        }
        


    }

    public void update() {
        Note toDelete = null;
        for (Note n: notes) {
            n.updatePosition();
            if ((n.getBottomBorder() >= gamePane.getHeight())) {
                    toDelete = n;
                    gamePane.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
                }
            if (checkCollision(n)) {
                    toDelete = n;
            }
        }
        if (toDelete != null) {
            notes.remove(toDelete);
            gamePane.getChildren().remove(toDelete);
        }


    }

    public void addNote() {

        Note note = new Note(gameTopPane.getPrefWidth() / 2, gameTopPane.getPrefHeight(), frameBeat);
        int leftBorder = (int)note.getRadius();
        int rightBorder = (int)gameTopPane.getPrefWidth() - (leftBorder);
        Random random = new Random();
        double randomInt;
        player.updatePosition();
        double possibleWidth = 0.2 * gamePane.getWidth();
        do {
            randomInt = random.nextInt(rightBorder - leftBorder) + leftBorder;
            System.out.println(player.getPosition());
        } while ((randomInt < (player.getPosition() - possibleWidth)) || (randomInt > player.getPosition() + possibleWidth));
        if (notes.size() > 0) {
            Note previousNote = notes.get(notes.size() - 1);
            if (randomInt > (previousNote.getCenterX() + (possibleWidth * 1.2))) {
                randomInt = previousNote.getCenterX() + (possibleWidth * 1.2);
            } else if (randomInt < (previousNote.getCenterX() - (possibleWidth * 1.2))) {
                randomInt = previousNote.getCenterX() - (possibleWidth * 1.2);
            }
        }
        note.setCenterX(randomInt);
        notes.add(note);
        gamePane.getChildren().addAll(note);



    }
    public boolean checkCollision(Note n) {
        if ((n.getBottomBorder() >= player.getLayoutY()) && (n.getCenterX() >= player.getLayoutX()) && (n.getCenterX() <= player.getLayoutX()+player.getFitWidth())   ) {
            this.score++;
            gameTopPane.setScore(getScore());
            System.out.println("COLLISION");
            return true;
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}