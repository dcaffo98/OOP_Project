package application.Graphics.item.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class ScoreScene extends Scene {

    private int maxCombo;
    private String songName;
    private int score;
    private int missedNotes;
    private int hitNotes;

    public ScoreScene(Parent parent, int maxCombo, String songName, int score, int missedNotes, int hitNotes) {
        super(parent);
        this.maxCombo = maxCombo;
        this.songName = songName;
        this.score = score;
        this.missedNotes = missedNotes;
        this.hitNotes = hitNotes;
    }

    public int getMaxCombo() {
        return maxCombo;
    }

    public String getSongName() {
        return songName;
    }

    public int getScore() {
        return score;
    }

    public int getMissedNotes() {
        return missedNotes;
    }

    public int getHitNotes() {
        return hitNotes;
    }
}
