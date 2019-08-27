package application.Graphics.FXML;

import application.Graphics.item.panes.SubScenePane;
import application.Graphics.item.scenes.MainSubScene;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainMenuController {

    @FXML
    private BorderPane mainMenuBorderPanel;

    @FXML
    private ImageView title;

    @FXML
    private VBox mainBox;

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
        
        mainBox.prefHeightProperty().bind(mainMenuBorderPanel.heightProperty());
        mainBox.prefWidthProperty().bind(mainMenuBorderPanel.widthProperty());
        //Title.fitWidthProperty().bind(MainMenuBorderPanel.widthProperty());
        title.fitHeightProperty().bind(mainMenuBorderPanel.heightProperty());

        mainMenuBorderPanel.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                title.setFitWidth(mainMenuBorderPanel.getWidth() * 0.3);
                // 4 bottoni, 3 spacing  15 10 15 10 15 10 15
                //MainBox.setSpacing(MainBox.getHeight() * 0.1);

                mainBox.setPadding(new Insets(mainBox.getHeight() * 0.05, mainBox.getWidth() * 0.05, mainBox.getHeight() * 0.05, mainBox.getWidth() * 0.05));
                for (Button b: buttons) {

                    b.setPrefWidth(mainBox.getWidth() * 0.2);
                    //b.setPrefHeight(MainBox.getHeight() * 0.15);
                }

            }
        });

        mainMenuBorderPanel.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //Title.setFitHeight(MainMenuBorderPanel.getHeight() * 0.3);
                // 4 bottoni, 3 spacing  15 10 15 10 15 10 15
                mainBox.setSpacing(mainBox.getHeight() * 0.1);
                for (Button b: buttons) {
                    b.setPrefHeight(mainBox.getHeight() * 0.15);
                }

            }
        });

    }

    @FXML
    public void playOnClick() throws Exception {
        System.out.println("Play!");
        Parent playPane = FXMLLoader.load(getClass().getResource("PlayPane.fxml"));
        mainMenuBorderPanel.getChildren().remove(mainBox);
        mainMenuBorderPanel.setCenter(playPane);
    }

    @FXML
    public void scoreOnClick() {
        System.out.println("Score!");
        scoreSubScene = new MainSubScene(new SubScenePane(mainMenuBorderPanel));
        mainBox.setVisible(false);
    }

    @FXML
    public void songsOnClick() throws Exception{
        System.out.println("Songs!");
        Parent songsPane = FXMLLoader.load(getClass().getResource("SongsPane.fxml"));
        mainMenuBorderPanel.getChildren().remove(mainBox);
        mainMenuBorderPanel.setCenter(songsPane);
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
        mainMenuBorderPanel.getChildren().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println("Changed!");
                boolean subSceneShowed = false;
                for (Node node : mainMenuBorderPanel.getChildren()) {
                    //se tra gli elementi viene trovato un SubScenePane, significa che una SubScene è aperta e quindi il menu non deve essere visualizzato
                    if (node instanceof Parent){
                        subSceneShowed = true;
                        break;
                    }
                    else
                        subSceneShowed = false;
                }

                if (!subSceneShowed)
                    mainMenuBorderPanel.setCenter(mainBox);
            }
        });
    }
}
