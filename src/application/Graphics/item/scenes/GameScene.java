package application.Graphics.item.scenes;

import application.Graphics.item.PlayerBar;
import application.Graphics.item.panes.GameTopPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class GameScene extends Scene {

    private AnchorPane gamePane;
    private GameTopPane gameTopPane;
    private PlayerBar playerBar;

    public GameScene(Parent root, double width, double height) {
        super(root, width, height);
        gamePane = (AnchorPane) root;
        gameTopPane = new GameTopPane();
        //questo e' un test
        playerBar = new PlayerBar("resources/images/paddleRed.png");
        gamePane.getChildren().addAll(gameTopPane, playerBar);
        setKeyListener();
    }

    public void setKeyListener() {
        setOnKeyPressed((KeyEvent key) -> {
            if (key.getCode().equals(KeyCode.RIGHT)) {
                playerBar.moveRight();
            }
            if (key.getCode().equals(KeyCode.LEFT)) {
                playerBar.moveLeft();
            }
        });
    }

}
