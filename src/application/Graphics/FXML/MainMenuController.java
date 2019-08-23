package application.Graphics.FXML;

import application.Graphics.item.panes.SubScenePane;
import application.Graphics.item.scenes.MainSubScene;
import application.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainMenuController {

    @FXML
    private BorderPane MainMenuBorderPanel;

    @FXML
    private ImageView Title;

    @FXML
    private VBox MainBox;

    @FXML
    private Button playButton;

    @FXML
    private Button scoreButton;

    @FXML
    private Button songsButton;

    @FXML
    private Button exitButton;

    private List<Button> buttons;
    private MainSubScene playSubScene;
    private MainSubScene scoreSubScene;
    private MainSubScene songsSubScene;

    public MainMenuController(){
    }


    public void initialize() {

        buttons = new ArrayList<Button>();
        buttons.add(playButton);
        buttons.add(scoreButton);
        buttons.add(songsButton);
        buttons.add(exitButton);

        //listener che visualizza il menu quando non ci sono SubScene aperte
        showMenu();

        MainBox.prefHeightProperty().bind(MainMenuBorderPanel.heightProperty());
        MainBox.prefWidthProperty().bind(MainMenuBorderPanel.widthProperty());
        //Title.fitWidthProperty().bind(MainMenuBorderPanel.widthProperty());
        Title.fitHeightProperty().bind(MainMenuBorderPanel.heightProperty());

        MainMenuBorderPanel.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Title.setFitWidth(MainMenuBorderPanel.getWidth() * 0.3);
                // 4 bottoni, 3 spacing  15 10 15 10 15 10 15
                //MainBox.setSpacing(MainBox.getHeight() * 0.1);
                MainBox.setPadding(new Insets(MainBox.getHeight() * 0.05, MainBox.getWidth() * 0.05, MainBox.getHeight() * 0.05, MainBox.getWidth() * 0.05));
                for (Button b: buttons) {

                    b.setPrefWidth(MainBox.getWidth() * 0.2);
                    //b.setPrefHeight(MainBox.getHeight() * 0.15);
                }

            }
        });

        MainMenuBorderPanel.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //Title.setFitHeight(MainMenuBorderPanel.getHeight() * 0.3);
                // 4 bottoni, 3 spacing  15 10 15 10 15 10 15
                MainBox.setSpacing(MainBox.getHeight() * 0.1);
                for (Button b: buttons) {
                    b.setPrefHeight(MainBox.getHeight() * 0.15);
                }

            }
        });

    }

    @FXML
    public void playOnClick() {
        System.out.println("Play!");
        playSubScene = new MainSubScene(new SubScenePane(MainMenuBorderPanel));
        System.out.println("SubScene width: " + playSubScene.getWidth() + "   SubScenePane width: " + ((SubScenePane) playSubScene.getRoot()).getWidth());
        MainBox.setVisible(false);
    }

    @FXML
    public void scoreOnClick() {
        System.out.println("Score!");
        scoreSubScene = new MainSubScene(new SubScenePane(MainMenuBorderPanel));
        MainBox.setVisible(false);
    }

    @FXML
    public void songsOnClick() {
        System.out.println("Songs!");
        songsSubScene = new MainSubScene(new SubScenePane(MainMenuBorderPanel));
        MainBox.setVisible(false);
    }

    @FXML
    public void exitOnClick() {
        System.out.println("Exit!");
        ((Stage) exitButton.getScene().getWindow()).close();
    }


    public void showMenu() {
        /*
         Questo listener viene chiamato ogni volta che un elemento viene aggiunto/eliminato dalla root (cioè il BorderPane)
         */
        MainMenuBorderPanel.getChildren().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println("Changed!");
                boolean subSceneShowed = false;
                for (Node node : MainMenuBorderPanel.getChildren()) {
                    //se tra gli elementi viene trovato un SubScenePane, significa che una SubScene è aperta e quindi il menu non deve essere visualizzato
                    if (node instanceof SubScenePane){
                        subSceneShowed = true;
                        break;
                    }
                    else
                        subSceneShowed = false;
                }

                if (!subSceneShowed)
                    MainBox.setVisible(true);
            }
        });
    }
}
