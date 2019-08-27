package application.Graphics.item;

import javafx.scene.control.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongsListView extends ListView<String> {

    private File directory;
    private List<String> songsList;

    public SongsListView() {
        directory = new File("C://Users/david/Desktop/RythmUp");
        songsList = new ArrayList<String>(Arrays.asList(directory.list()));
        for (String song : songsList) {
            System.out.println(song);
            getItems().add(song);
        }
    }

    public void addItems() {
        getItems().addAll(songsList);
    }
}
