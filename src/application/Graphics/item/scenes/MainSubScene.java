package application.Graphics.item.scenes;

import application.Graphics.item.panes.SubScenePane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SubScene;
import javafx.scene.control.Button;

public class MainSubScene extends SubScene {

    private SubScenePane root;
    private Button backButton;

    public MainSubScene(SubScenePane root) {
        super(root, root.getWidth() , root.getHeight());
        this.root = root;
        root.getParentPane().getChildren().add(root);

        backButton = new Button("Back");
        root.getChildren().add(backButton);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //rimuove questa sottoscena eliminando il nodo root dal suo genitore
                //il casting a Pane serve per poter usare il metodo getChildren()
                root.getParentPane().getChildren().remove(root);
            }
        });

    }
}
