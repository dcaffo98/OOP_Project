package application.Graphics.FXML;

import application.Graphics.item.ParentGetter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SongsPaneController {

    private Pane root;
    private ParentGetter parentGetter;

    @FXML
    private BorderPane songsBorderPane;

    @FXML
    private Button backButton;

    @FXML
    private ListView songs;

    public SongsPaneController() {
    }

    public void initialize() {
        parentGetter = new ParentGetter(songsBorderPane, root) {
            @Override
            public void parentGotten() {
                setRootPane((Pane) getParent());
            }
        };
    }

    public void setRootPane(Pane root) {
        this.root = root;
    }


    @FXML
    public void backButtonCliked(ActionEvent event) {
        root.getChildren().remove(songsBorderPane);
    }
}
