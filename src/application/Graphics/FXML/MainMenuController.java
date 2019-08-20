package application.Graphics.FXML;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainMenuController {

    @FXML
    BorderPane MainMenuBorderPanel;

    @FXML
    ImageView Titolo;

    @FXML
    VBox MainBox;


    public MainMenuController(){
    }


    public void initialize() {


        System.out.println("sono stato qui");
        MainBox.prefHeightProperty().bind(MainMenuBorderPanel.heightProperty());
        MainBox.prefWidthProperty().bind(MainMenuBorderPanel.widthProperty());
        Titolo.fitWidthProperty().bind(MainMenuBorderPanel.widthProperty());
        Titolo.fitHeightProperty().bind(MainMenuBorderPanel.heightProperty());

    }

}
