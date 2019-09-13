package application;

import application.Graphics.Viewer;
import application.Graphics.item.MongoDBConnector;
import application.Graphics.item.stages.MainStage;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import javafx.application.Application;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.File;
import java.io.FileOutputStream;

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
