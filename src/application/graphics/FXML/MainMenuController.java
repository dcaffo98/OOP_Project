package application.graphics.FXML;

import application.graphics.item.MongoDBConnector;
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

    private MongoDBConnector mongoDBConnector;

    @FXML
    private BorderPane mainMenuBorderPane;

    @FXML
    private ImageView title;

    @FXML
    private VBox mainBox;

    @FXML
    private Button songScoreButton;

    @FXML
    private Button highScoreButton;

    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    private List<Button> buttons;

    @FXML
    public void initialize() {

        mongoDBConnector = new MongoDBConnector();

        buttons = new ArrayList<Button>();
        buttons.add(songScoreButton);
        buttons.add(highScoreButton);
        buttons.add(playButton);
        buttons.add(exitButton);

        //binding elements
        title.fitWidthProperty().bind(mainMenuBorderPane.widthProperty());
        title.fitHeightProperty().bind(mainMenuBorderPane.heightProperty().multiply(0.4));
        mainBox.prefHeightProperty().bind(mainMenuBorderPane.heightProperty().multiply(0.6));
        mainBox.spacingProperty().bind(mainMenuBorderPane.heightProperty().multiply(0.07));
        for (Button button : buttons) {
            button.prefWidthProperty().bind(mainMenuBorderPane.widthProperty().multiply(0.11));
            button.prefHeightProperty().bind(mainMenuBorderPane.heightProperty().multiply(0.07));
            button.setMaxHeight(60);
        }

        //listener che visualizza il menu quando non ci sono SubScene aperte
        showMenu();
    }

    /*
    apre la finestra di selezione canzoni per giocare
     */
    @FXML
    public void playOnClick(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PlayPane.fxml"));
        Parent playPane = loader.load();
        PlayPaneController controller = loader.getController();
        controller.setMongoDBConnector(this.mongoDBConnector);
        controller.setListViewItem();
        mainMenuBorderPane.getChildren().remove(mainBox);
        mainMenuBorderPane.setCenter(playPane);
    }

    /*
    apre la finestra per visualizzare il punteggio di una data canzone
     */
    @FXML
    public void scoreButtonClicked(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SongScorePane.fxml"));
        Parent songScorePane = loader.load();
        SongScoreController controller = loader.getController();
        controller.setMongoDBConnector(this.mongoDBConnector);
        controller.setComboBoxItems();
        mainMenuBorderPane.getChildren().remove(mainBox);
        mainMenuBorderPane.setCenter(songScorePane);
    }

    /*
    apre la finestra per visualizzare il ranking dei punteggi più alti
     */
    @FXML
    public void highScoreButtonClicked(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HighScorePane.fxml"));
        Parent highScorePane = loader.load();
        HighScorePaneController controller = loader.getController();
        controller.setMongoDBConnector(this.mongoDBConnector);
        controller.setTableViewItems();
        mainMenuBorderPane.getChildren().remove(mainBox);
        mainMenuBorderPane.setCenter(highScorePane);
    }

    /*
    chiude lo stage principale, terminando l'applicazione
     */
    @FXML
    public void exitOnClick(ActionEvent event) {
        ((Stage) exitButton.getScene().getWindow()).close();
    }


    /*
        Questo listener viene chiamato ogni volta che un elemento viene aggiunto/eliminato dallo scene-graph con radice il mainMenuBorderPane
    */
    public void showMenu() {
        mainMenuBorderPane.getChildren().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                boolean subSceneShowed = false;
                for (Node node : mainMenuBorderPane.getChildren()) {
                    //se tra gli elementi dello scene-graph viene trovato un Parent, significa che un bottone è stato premuto e quindi il menu non deve essere visualizzato
                    if (node instanceof Parent){
                        subSceneShowed = true;
                        break;
                    }
                    else
                        subSceneShowed = false;
                }
                /*
                    se non viene torvato un elemento Parent, deve essere visualizzato il menu principale, costituito dai 4 bottoni racchiusi nella Vbox,
                    la quale sarà posta nella sezione centrale del BorderPane
                */
                if (!subSceneShowed) {
                    mainMenuBorderPane.setCenter(mainBox);
                    mainMenuBorderPane.requestFocus();
                }
            }
        });
    }

    public MongoDBConnector getMongoDBConnector() {
        return mongoDBConnector;
    }

    public void setMongoDBConnector(MongoDBConnector mongoDBConnector) {
        this.mongoDBConnector = mongoDBConnector;
    }
}
