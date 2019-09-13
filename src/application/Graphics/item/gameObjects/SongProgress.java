package application.Graphics.item.gameObjects;

import application.Graphics.item.panes.GameTopPane;
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
