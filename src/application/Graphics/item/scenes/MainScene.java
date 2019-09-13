package application.Graphics.item.scenes;

import application.Graphics.item.MongoDBConnector;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainScene extends Scene {

    private MongoDBConnector mongoDBConnector;

    public MainScene(Parent root, double width, double height) {
        super(root, width, height);
        getStylesheets().add("resources/style/stylesheet.css");
    }

}
