package application;

import application.graphics.item.MongoDBConnector;
import application.graphics.item.stages.MainStage;
import javafx.application.Application;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            MongoDBConnector mongoDBConnector = new MongoDBConnector();
            MainStage mainStage = new MainStage(800, 600, "RythmUp", mongoDBConnector);
            mainStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args) {
        launch(args);
    }
}
