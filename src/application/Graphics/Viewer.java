package application.Graphics;

import application.Graphics.item.scenes.MainSubScene;
import application.Graphics.item.MyButton;
import application.Graphics.item.stages.MainStage;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Viewer {
//attributes surely useful
    private Stage mainStage;



    private DoubleProperty sceneWidth;
    private  DoubleProperty sceneHeight;
    private double subSceneWidth;
    private double subSceneHeight;
    private double subSceneLayoutX;
    private double subSceneLayoutY;
    private boolean subSceneShowed;

    private AnchorPane mainPane;
    private Scene mainScene;

    private TilePane menu;
    private Label title;
    private MyButton play;
    private MyButton score;
    private MyButton songs;
    private MyButton exit;
    private List<MyButton> menuButtons = new ArrayList<MyButton>();
    private MainSubScene subScene;

    public Viewer() throws Exception {
        //mainStage = new MainStage(800, 600, "RythmUp");
        //mainStage.minHeightProperty().bind(mainStage.widthProperty().multiply(0.5));
        //mainStage.maxHeightProperty().bind(mainStage.widthProperty().multiply(0.5));


        /*
        sceneWidth.bind(mainPane.widthProperty());

        sceneHeight = new SimpleDoubleProperty();
        sceneHeight.bind(mainPane.heightProperty());
        subSceneWidth = sceneWidth.get() * 0.6;
        subSceneHeight = sceneHeight.get() * 0.5;
        subSceneLayoutX = sceneWidth.get() * 0.2;
        subSceneLayoutY = sceneHeight.get() * 0.4;
        */
       /*
        setTitle();
        createPlayButton();
        createScoreButton();
        createSongsButton();
        createExitButton();
        setMenuButtons();
        setMenu();
        mainPane.requestFocus();
        setMainSceneChangeListener();
       manageSubScene();
 */
    }

    public Stage getMainStage() {
        return mainStage;
    }
/*


    public void createPlayButton() {
        play = new myButton("Play");
        menuButtons.add(play);
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                subScene = createSubScene();
            }
        });
    }

    public void createScoreButton() {
        score = new myButton("Score");
        menuButtons.add(score);
        score.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
    }

    public void createSongsButton() {
        songs = new myButton("Songs");
        menuButtons.add(songs);
    }

    public void createExitButton() {
        exit = new myButton("Exit");
        menuButtons.add(exit);
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    public void setMenuButtons() {
        for (myButton button : menuButtons) {
            button.setMaxWidth(Double.MAX_VALUE);
        }
    }

    public void setMenu() {
        menu.setLayoutX(mainPane.getWidth() * 0.4);
        menu.setLayoutY(mainPane.getHeight() * 0.4);
        menu.setPrefWidth(mainPane.getWidth() * 0.2);
        menu.setPrefHeight(mainPane.getHeight() * 0.5);
        menu.setPrefTileWidth(mainPane.getWidth() * 0.2);
        menu.setPrefTileHeight(mainPane.getHeight() * 0.5 / menuButtons.size());
        menu.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        for (myButton button : menuButtons)
            menu.getChildren().add(button);
        mainPane.getChildren().add(menu);
    }

    //ridimensionare dinamicamente elementi della scena principale




    public void setTitle() {
        title.setLayoutY(mainPane.getHeight() * 0.1);
        title.setLayoutX(mainPane.getWidth() * 0.2);
        title.setPrefHeight(mainPane.getHeight() * 0.2);
        title.setPrefWidth(mainPane.getWidth() * 0.6);
        title.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        title.setAlignment(Pos.CENTER);
        title.setFont(new Font("Verdana", 40));
        mainPane.getChildren().add(title);
    }

    public MainSubScene createSubScene() {
        MainSubScene subScene = new MainSubScene(new AnchorPane(), subSceneWidth, subSceneHeight);
        mainPane.getChildren().add(subScene);
        subSceneShowed = true;
        subScene.setLayoutX(subSceneLayoutX);
        subScene.setLayoutY(subSceneLayoutY);
        return subScene;
    }

    public void manageSubScene() {
        subScene.toShow.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue.booleanValue() == true) {
                    subSceneShowed = true;
                    System.out.println("subSceneShowed!");
                } else {
                    subSceneShowed = false;
                    mainPane.getChildren().remove(subScene);
                    System.out.println("subSceneNotShowed!");
                }
            }
        });
    }
    */


}
