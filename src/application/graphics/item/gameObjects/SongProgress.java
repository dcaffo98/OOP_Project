package application.graphics.item.gameObjects;

import application.graphics.item.panes.GameTopPane;
import javafx.scene.control.ProgressBar;

public class SongProgress extends ProgressBar {

    public SongProgress(GameTopPane topPane) {
        super();
        this.setLayoutX(topPane.getPrefWidth() - 250);
        this.setPrefWidth(200);
        this.setLayoutY(20);
        this.setProgress(100);
    }
}
