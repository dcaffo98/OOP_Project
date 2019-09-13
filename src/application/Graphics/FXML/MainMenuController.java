package application.Graphics.FXML;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private Button playButtonDeprecated;

    @FXML
    private Button scoreButton;

    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    private List<Button> buttons;

    @FXML
    public void initialize() {

        buttons = new ArrayList<Button>();
        buttons.add(playButtonDeprecated);
        buttons.add(scoreButton);
        buttons.add(playButton);
        buttons.add(exitButton);

        //binding elements
        title.fitWidthProperty().bind(mainMenuBorderPanel.widthProperty());
        title.fitHeightProperty().bind(mainMenuBorderPanel.heightProperty().multiply(0.4));
        mainBox.prefHeightProperty().bind(mainMenuBorderPanel.heightProperty().multiply(0.6));
        mainBox.spacingProperty().bind(mainMenuBorderPanel.heightProperty().multiply(0.07));
        for (Button button : buttons) {
            button.prefWidthProperty().bind(mainMenuBorderPanel.widthProperty().multiply(0.1));
            button.prefHeightProperty().bind(mainMenuBorderPanel.heightProperty().multiply(0.07));
            button.setMaxHeight(60);
        }

        //listener che visualizza il menu quando non ci sono SubScene aperte
        showMenu();
    }

    @FXML
    public void playOnClick(ActionEvent event) throws Exception {
        System.out.println("Play!");
        Parent songsPane = FXMLLoader.load(getClass().getResource("PlayPane.fxml"));
        mainMenuBorderPanel.getChildren().remove(mainBox);
        mainMenuBorderPanel.setCenter(songsPane);
    }

    @FXML
    public void playOnClickDeprecated(ActionEvent event) throws Exception {
        System.out.println("Play!");
        Parent playPane = FXMLLoader.load(getClass().getResource("PlayPaneDeprecated.fxml"));
        mainMenuBorderPanel.getChildren().remove(mainBox);
        mainMenuBorderPanel.setCenter(playPane);
    }

    @FXML
    public void scoreOnClick(ActionEvent event) throws Exception {
        System.out.println("Score!");
        Parent scorePane = FXMLLoader.load(getClass().getResource("ScorePane.fxml"));
        mainMenuBorderPanel.getChildren().remove(mainBox);
        mainMenuBorderPanel.setCenter(scorePane);
    }

    @FXML
    public void exitOnClick(ActionEvent event) {
        System.out.println("Exit!");
        ((Stage) exitButton.getScene().getWindow()).close();
    }


    /*
        Questo listener viene chiamato ogni volta che un elemento viene aggiunto/eliminato dalla root (cioè il BorderPane)
    */
    public void showMenu() {
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
