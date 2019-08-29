package application.Graphics.item.gameObjects;

import application.Graphics.item.panes.GameTopPane;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class FrameHandler implements EventHandler<ActionEvent> {

    public KeyCode code;
    public GameTopPane gameTopPane;
    public PlayerBar player;
    public Timeline timeline;
    public ArrayList<Note> notes;
    public AnchorPane gamePane;
    public int score;

    public FrameHandler(AnchorPane gamePane,GameTopPane gameTopPane, PlayerBar player, Timeline timeline, ArrayList<Note> notes) {

        this.gamePane = gamePane;
        this.gameTopPane = gameTopPane;
        this.player = player;
        this.timeline = timeline;
        this.notes = notes;
        this.score = 0;
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
        if (code != null) {
            switch (code) {
                case RIGHT:
                    player.moveRight();
                    break;
                case LEFT:
                    player.moveLeft();
                    break;
            }
        }
        if (notes.size() > 0) {
            update();
        }


    }

    public void update() {
        Note toDelete = null;
        for (Note n: notes) {
            n.updatePosition();
            if ((n.getBottomBorder() >= gamePane.getHeight()) || checkCollision(n)) {
                    toDelete = n;
                }
        }
        if (toDelete != null) {
            notes.remove(toDelete);
            gamePane.getChildren().remove(toDelete);
        }


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