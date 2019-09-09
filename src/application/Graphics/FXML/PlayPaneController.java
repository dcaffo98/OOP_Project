package application.Graphics.FXML;

import application.Graphics.item.scenes.GameScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;


public class PlayPaneController {

    private final String songsPath = "src/trackanalyzer/songs";
    private ObservableList<String> songs = FXCollections.observableArrayList(new File(songsPath).list());
    private Media selectedSong;

    @FXML
    private BorderPane playBorderPane;

    @FXML
    private ComboBox<String> songsComboBox;

    @FXML
    private Button backButton;

    @FXML
    private Button playButton;

    @FXML
    public void initialize () {
        songsComboBox.getItems().setAll(songs);
    }

    @FXML
    public void backButtonClicked(ActionEvent event) {
        ((Pane) playBorderPane.getParent()).getChildren().remove(playBorderPane);
    }

    @FXML
    public void playButtonClicked(ActionEvent event) {
        ((Stage) playBorderPane.getParent().getScene().getWindow()).close();
        System.out.println("Let's go!");
        Stage playStage = new Stage();
        playStage.setMinWidth(1200);
        playStage.setMinHeight(800);
        playStage.setScene(new GameScene(new AnchorPane(), playStage.getMinWidth(), playStage.getMinHeight(),this.selectedSong));
        playStage.setTitle("RythmUp");
        playStage.show();
    }

    @FXML
    public void songsComboBoxSelectionChange(ActionEvent event) {
        this.selectedSong = new Media(Paths.get(songsPath + "/" + songsComboBox.getSelectionModel().getSelectedItem().toString()).toUri().toString());
        System.out.println(songsComboBox.getSelectionModel().getSelectedItem().toString());

    }
}
