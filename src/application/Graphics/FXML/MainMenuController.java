package application.Graphics.FXML;

import application.Graphics.item.panes.MainPane;
import application.Graphics.item.scenes.MainScene;
import application.Graphics.item.stages.MainStage;
import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MainMenuController {

    @FXML
    BorderPane MainMenuBorderPanel;

    @FXML
    ImageView Title;

    @FXML
    VBox MainBox;

    @FXML
    Button playButton;

    @FXML
    Button scoreButton;

    @FXML
    Button songsButton;

    @FXML
    Button exitButton;

    private List<Button> buttons;

    public MainMenuController(){
    }


    public void initialize() {

        buttons = new ArrayList<Button>();
        buttons.add(playButton);
        buttons.add(scoreButton);
        buttons.add(songsButton);
        buttons.add(exitButton);

        MainBox.prefHeightProperty().bind(MainMenuBorderPanel.heightProperty());
        MainBox.prefWidthProperty().bind(MainMenuBorderPanel.widthProperty());
        //Title.fitWidthProperty().bind(MainMenuBorderPanel.widthProperty());
        Title.fitHeightProperty().bind(MainMenuBorderPanel.heightProperty());
        //playButton.prefWidthProperty().bind(MainBox.widthProperty());
        //playButton.prefHeightProperty().bind(MainBox.heightProperty());

        MainMenuBorderPanel.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Title.setFitWidth(MainMenuBorderPanel.getWidth() * 0.3);
                // 4 bottoni, 3 spacing  15 10 15 10 15 10 15
                MainBox.setSpacing(MainBox.getHeight() * 0.1);
                MainBox.setPadding(new Insets(MainBox.getHeight() * 0.05, MainBox.getWidth() * 0.05, MainBox.getHeight() * 0.05, MainBox.getWidth() * 0.05));
                for (Button b: buttons) {

                    b.setPrefWidth(MainBox.getWidth() * 0.2);
                    b.setPrefHeight(MainBox.getHeight() * 0.15);
                }

            }
        });
    }


    @FXML
    public void playOnClick () {

        System.out.println("Clikkato!!");
        //Parent root = FXMLLoader.load(getClass().getResource("mainGUI.fxml"));
        MainStage mainStage = (MainStage) MainMenuBorderPanel.getScene().getWindow();
        mainStage.setScene(new MainScene(new MainPane(), mainStage.getWidth(), mainStage.getHeight()));

    }

}
