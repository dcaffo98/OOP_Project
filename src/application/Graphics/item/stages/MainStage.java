package application.Graphics.item.stages;

import application.Graphics.item.panes.MainPane;
import application.Graphics.item.scenes.MainScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStage extends Stage {

    private MainScene mainScene;
    private MainPane mainPane;


    public MainStage(double width, double height, String title) throws Exception {
        setWidth(width);
        setHeight(height);
        setMinHeight(height);
        setMinWidth(width);
        setMainPane(new MainPane());
        Parent root = FXMLLoader.load(getClass().getResource("../../MainMenu.fxml"));
        setMainScene(new MainScene(root,getWidth(),getHeight()));
        getMainPane().bindScene(getMainScene());
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