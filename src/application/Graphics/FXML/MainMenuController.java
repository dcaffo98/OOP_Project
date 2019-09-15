package application.Graphics.FXML;

import application.Graphics.item.MongoDBConnector;
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
    private BorderPane mainMenuBorderPanel;

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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PlayPane.fxml"));
        Parent playPane = loader.load();
        PlayPaneController controller = loader.getController();
        controller.setMongoDBConnector(this.mongoDBConnector);
        controller.setListViewItem();
        mainMenuBorderPanel.getChildren().remove(mainBox);
        mainMenuBorderPanel.setCenter(playPane);
    }

    @FXML
    public void scoreButtonClicked(ActionEvent event) throws Exception {
        System.out.println("SongScore");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SongScorePane.fxml"));
        Parent songScorePane = loader.load();
        SongScoreController controller = loader.getController();
        controller.setMongoDBConnector(this.mongoDBConnector);
        controller.setComboBoxItems();
        mainMenuBorderPanel.getChildren().remove(mainBox);
        mainMenuBorderPanel.setCenter(songScorePane);
    }

    @FXML
    public void highScoreButtonClicked(ActionEvent event) throws Exception {
        System.out.println("HighScore!");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HighScorePane.fxml"));
        Parent highScorePane = loader.load();
        HighScorePaneController controller = loader.getController();
        controller.setMongoDBConnector(this.mongoDBConnector);
        controller.setTableViewItems();
        mainMenuBorderPanel.getChildren().remove(mainBox);
        mainMenuBorderPanel.setCenter(highScorePane);
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

    public MongoDBConnector getMongoDBConnector() {
        return mongoDBConnector;
    }

    public void setMongoDBConnector(MongoDBConnector mongoDBConnector) {
        this.mongoDBConnector = mongoDBConnector;
    }
}
