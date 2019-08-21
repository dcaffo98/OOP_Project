package application.Graphics.item.scenes;

import application.Graphics.item.panes.SubScenePane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SubScene;
import javafx.scene.control.Button;


public class MainSubScene extends SubScene {

    private SubScenePane root;
    private Button backButton;
    private BooleanProperty isShowed;

    public MainSubScene(SubScenePane root) {
        super(root, root.getWidth(), root.getHeight());
        this.root = root;
        backButton = new Button("Back");
        this.root.getChildren().add(backButton);
        isShowed = new SimpleBooleanProperty();
        isShowed.set(true);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isShowed.set(false);
            }
        });
    }

    public BooleanProperty isShowedProperty() {
        return isShowed;
    }

    public void setIsShowed(boolean isShowed) {
        this.isShowed.set(isShowed);
    }
}
