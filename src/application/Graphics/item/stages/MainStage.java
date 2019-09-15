package application.Graphics.item.stages;

import application.Graphics.FXML.MainMenuController;
import application.Graphics.FXML.ResultPaneController;
import application.Graphics.item.MongoDBConnector;
import application.Graphics.item.panes.MainPane;
import application.Graphics.item.scenes.MainScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainStage extends Stage {

    private MainScene mainScene;
    private MainPane mainPane;


    public MainStage(double width, double height, String title, MongoDBConnector mongoDBConnector) throws Exception {
        setWidth(width);
        setHeight(height);
        setMinHeight(height);
        setMinWidth(width);
        setMainPane(new MainPane());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../FXML/MainMenu.fxml"));
        Parent root = loader.load();
        MainMenuController controller = loader.getController();
        controller.setMongoDBConnector(mongoDBConnector);
        setMainScene(new MainScene(root,getWidth(),getHeight()));
        setScene(getMainScene());
        setTitle(title);
    }


    public MainScene getMainScene() {
        return mainScene;
    }

    public void setMainScene(MainScene mainScene) {
        this.mainScene = mainScene;

    }

    public MainPane getMainPane() {
        return mainPane;
    }

    public void setMainPane(MainPane mainPane) {
        this.mainPane = mainPane;
    }




}