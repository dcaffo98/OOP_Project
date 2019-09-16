package application.Graphics.item.stages;

import application.Graphics.FXML.MainMenuController;
import application.Graphics.item.MongoDBConnector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Stage {

    public MainStage(double width, double height, String title, MongoDBConnector mongoDBConnector) throws Exception {
        setWidth(width);
        setHeight(height);
        setMinHeight(height);
        setMinWidth(width);
        setTitle(title);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../FXML/MainMenu.fxml"));
        Parent root = loader.load();
        MainMenuController controller = loader.getController();
        controller.setMongoDBConnector(mongoDBConnector);
        setScene(new Scene(root, getWidth(), getHeight()));
        root.requestFocus();
    }

}