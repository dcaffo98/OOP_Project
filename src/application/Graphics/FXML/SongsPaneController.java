package application.Graphics.FXML;

import application.Graphics.item.ParentGetter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class SongsPaneController {

    private Pane root;
    private ParentGetter parentGetter;
    private MediaPlayer mediaPlayer;
    private Media media;
    //da eliminare dopo integrazione database
    private final String songsPath = "src/resources/songs";

    @FXML
    private BorderPane songsBorderPane;

    @FXML
    private Button backButton;

    @FXML
    private Button uploadSongButton;

    @FXML
    private ListView songsListView;

    public SongsPaneController() {}

    public void initialize() {
        parentGetter = new ParentGetter(songsBorderPane, root) {
            @Override
            public void parentGotten() {
                setRootPane((Pane) getParent());
            }
        };

        populateListView();

        songsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                media = new Media(Paths.get(songsPath + "/" + songsListView.getSelectionModel().getSelectedItem().toString()).toUri().toString());
                if (mediaPlayer != null) {
                    mediaPlayer.dispose();
                }
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            }
        });
    }

    public void setRootPane(Pane root) {
        this.root = root;
    }

    public void populateListView() {
        LinkedHashSet<String> songListWithoutDuplicate = new LinkedHashSet<>(Arrays.asList(new File(songsPath).list()));
        for (String song : songListWithoutDuplicate) {
            songsListView.getItems().add(song);
        }
    }

    @FXML
    public void uploadSongButtonClicked(ActionEvent event) {
        List<File> fileList = new FileChooser().showOpenMultipleDialog(songsBorderPane.getScene().getWindow());
        if (fileList != null) {
            for (File file : fileList) {
                //String filename = new File("C://Users/david/Desktop/RythmUp").getPath() + "\\" + file.getName();
                Path filenamex = Paths.get(songsPath + "/" + file.getName());
                File copyFile = new File(filenamex.toUri());
                try {
                    copyFile.createNewFile();
                    FileInputStream source = new FileInputStream(file);
                    FileOutputStream destination = new FileOutputStream(copyFile);
                    byte[] buffer = new byte[1024];
                    int lenght;
                    while ((lenght = source.read(buffer)) > 0) {
                        destination.write(buffer, 0, lenght);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (songsListView.getItems().contains(copyFile.getName())) {
                    System.out.println("This song is already available" + copyFile.getName());
                    continue;
                } else
                    songsListView.getItems().add(copyFile.getName());
            }
        } else {
            System.out.println("Invalid or no file detected");
        }
    }

    @FXML
    public void backButtonClicked(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }
        root.getChildren().remove(songsBorderPane);
    }

}
