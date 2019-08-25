package application.Graphics.item.scenes;

import application.Graphics.item.PlayerBar;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class GameScene extends Scene {

    private AnchorPane gamePane;
    private PlayerBar playerBar;

    public GameScene(Parent root, double width, double height) {
        super(root, width, height);
        gamePane = (AnchorPane) root;
        playerBar = new PlayerBar("resources/images/paddleRed.png");
        gamePane.getChildren().add(playerBar);
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
