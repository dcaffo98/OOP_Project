package application.Graphics.item.gameObjects;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class FrameHandler implements EventHandler<ActionEvent> {

    public KeyCode code;
    public PlayerBar player;
    public Timeline timeline;
    public ArrayList<Note> notes;
    public AnchorPane gamePane;

    public FrameHandler(AnchorPane gamePane,PlayerBar player, Timeline timeline, ArrayList<Note> notes) {

        this.gamePane = gamePane;
        this.player = player;
        this.timeline = timeline;
        this.notes = notes;
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
        System.out.println("numero note:"+ notes.size());
        System.out.println(gamePane.getHeight());

        for (Note n: this.notes) {
            n.updatePosition();
            if (n.getBottomBorder() >= gamePane.getHeight()) {

                notes.remove(n);
                gamePane.getChildren().remove(n);

            }

        }

        // move rects & check intersection
        /*final Paint color = circle.getFill();
        final double cx = circle.getCenterX();
        final double cy = circle.getCenterY();
        final double r2 = circle.getRadius() * circle.getRadius();
        boolean lost = false;
        for (Rectangle rect : rectangles) {
            rect.setY(frame * RECT_MAX_Y / framesPerIteration);
            // check for intersections with rects of wrong color
            if (rect.getFill() != color) {

                double dy = Math.min(Math.abs(rect.getY() - cy),
                        Math.abs(rect.getY() + rect.getHeight() - cy));
                dy = dy * dy;

                if (dy > r2) {
                    continue; // y-distance too great for intersection
                }
                if (cx >= rect.getX() && cx <= rect.getX() + rect.getWidth()) {
                    lost = true;
                } else {
                    double dx = Math.min(Math.abs(rect.getX() - cx),
                            Math.abs(rect.getX() + rect.getWidth() - cx));
                    if (dx * dx + dy <= r2) {
                        lost = true;
                    }
                }
            }
        }*/
       // frame = (frame + 1) % framesPerIteration;
        /*if (lost) {
            timeline.stop();
        }
        */
    }
}