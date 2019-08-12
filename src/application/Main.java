package application;

import application.Graphics.Viewer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Viewer viewer = new Viewer();
            primaryStage = viewer.getMainStage();
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args) {
        launch(args);
    }
}
